package com.example.uninsubriasurvive.navigation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.uninsubriasurvive.R
import com.example.uninsubriasurvive.database.entity.Pavilion
import com.example.uninsubriasurvive.modelview.model.exam.ExamEvent
import com.example.uninsubriasurvive.modelview.model.exam.ExamState
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionEvent
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionViewModel
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.modelview.view.home.BookletScreen
import com.example.uninsubriasurvive.modelview.view.home.ExamDetailsScreen
import com.example.uninsubriasurvive.modelview.view.home.ExamScreen
import com.example.uninsubriasurvive.modelview.view.home.MainPageScreen
import com.example.uninsubriasurvive.modelview.view.home.PavilionDetailsScreen
import com.example.uninsubriasurvive.modelview.view.home.PavilionScreen
import com.example.uninsubriasurvive.modelview.view.utility.LoginButton
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigationScreen(
    studentViewModel: StudentViewModel,
    examViewModel: ExamViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    pavilionViewModel: PavilionViewModel,
    navController: NavController
) {
    var text by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val examOnEvent = examViewModel::onEvent


    val studentState by studentViewModel.state.collectAsState()

    val items = listOf(
        Screen.Home,
        Screen.Exams,
        Screen.Booklet,
        Screen.Pavilion,
    )
    val navHomeController = rememberNavController()
    val navBackStackEntry by navHomeController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination




    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Text(
                        text = text.toUpperCase(Locale.ROOT),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                        AsyncImage(
                            model = studentState.profilePicture,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 22.dp)
                                .size(45.dp)
                                .clip(CircleShape)
                                .clickable {
                                    showDialog = true
                                }
                        )

                },
                navigationIcon = {
                    val navBackStackEntry by navHomeController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    if (currentDestination?.hierarchy?.any { it.route == "exam_details/{examId}" || it.route == "pavilion_details/{pavilionId}" } == true) {
                        IconButton(onClick = { navHomeController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }

            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background,
                elevation = 0.dp,
                modifier = Modifier.height(50.dp)

            ) {
                val navBackStackEntry by navHomeController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navHomeController.navigate(screen.route) {

                                popUpTo(navHomeController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },
                        icon = {
                            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                Icon(
                                    painterResource(id = screen.icon ) ,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier
                                        .size(35.dp)

                                )
                            }
                        })
                }
            }
        }
    ) { innerPadding ->

        if (showDialog) {
            ShowDialog(
                studentState,
                {showDialog = false },
                onSubmit = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed Out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("auth")
                    }
                }

            )
        }

            NavHost(
                navHomeController,
                startDestination = Screen.Home.route,
                Modifier.padding(innerPadding)
            ) {

                composable(Screen.Home.route) {
                    text = "Home"
                    MainPageScreen(navHomeController, studentState = studentState)
                }
                composable(Screen.Exams.route) {
                    text = "Esami"

                    examOnEvent(ExamEvent.GetAllWithDates)
                    ExamScreen(
                        navHomeController,
                        examViewModel,
                        studentViewModel
                    )
                }
                composable("exam_details/{examId}") {
                    text = "Orari Esame"
                    it.arguments?.getString("examId")?.let { it1 ->
                        examOnEvent(ExamEvent.GetExamWithDateById(it1.toInt()))
                        ExamDetailsScreen(
                            navHomeController = navHomeController ,
                            examViewModel = examViewModel,
                            studentViewModel = studentViewModel
                        )
                    }
                }
                composable(Screen.Booklet.route) {
                    text = "Libretto"
                    BookletScreen(
                        navHomeController,
                        studentViewModel = studentViewModel
                    )
                }
                composable(Screen.Pavilion.route) {
                    text = "Padiglioni"
                    val onEvent = pavilionViewModel::onEvent
                    val pavilionState by pavilionViewModel.state.collectAsState()
                    onEvent(PavilionEvent.GetAll)

                    PavilionScreen(
                        navHomeController,
                        pavilionState = pavilionState
                    )
                }
                composable("pavilion_details/{pavilionId}") {
                    var pavilionid = 0
                    var pavilion : Pavilion? = null
                    it.arguments?.getString("pavilionId")?.let { it ->
                        pavilionid = it.toInt()
                    }
                    val pavilionState by pavilionViewModel.state.collectAsState()
                    pavilionState.pavilions.forEach {
                        if (it.id == pavilionid) {
                            pavilion = it
                        }
                    }

                    PavilionDetailsScreen(
                        pavilion = pavilion!!,
                        context = context
                    )

                }
            }
        }
    }

    @Composable
    fun ShowDialog(
        studentState: StudentState,
        onDismissRequest: () -> Unit,
        onSubmit: () -> Unit
    ) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(375.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colorScheme.tertiary
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (studentState?.profilePicture != null) {
                        AsyncImage(
                            model = studentState.profilePicture,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (studentState?.firstName != null) {
                            Text(
                                text = studentState.firstName.toUpperCase(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (studentState?.lastName != null) {
                            Text(
                                text = studentState.lastName.toUpperCase(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    if (studentState?.emailAddress != null) {
                        Text(
                            text = studentState.emailAddress,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                "Dismiss",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                        TextButton(
                            onClick = { onSubmit() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                "Sign Out",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }
            }
        }
    }








