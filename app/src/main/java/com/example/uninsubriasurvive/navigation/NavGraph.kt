package com.example.uninsubriasurvive.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionEvent
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionViewModel
import com.example.uninsubriasurvive.modelview.model.student.UserEvent
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.modelview.view.CompleteRegistrationScreen
import com.example.uninsubriasurvive.modelview.view.SignInScreen
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import com.example.uninsubriasurvive.sign_in.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    studentViewModel: StudentViewModel,
    examViewModel: ExamViewModel,
    pavilionViewModel: PavilionViewModel
    ) {
    val navController = rememberNavController()
    val userOnEvent = studentViewModel::onEvent
    val studentState by studentViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "auth") {

        navigation(route = "auth", startDestination = "sign_in") {
            userOnEvent(UserEvent.ResetViewModel)

            composable("sign_in") {
                val viewModel = viewModel<SignInViewModel>()
                val signInState by viewModel.state.collectAsStateWithLifecycle()

                //SE VIENE TROVATO UN UTENTE LOGGATO SALTA LA FASE DI ACCESSO
                LaunchedEffect(key1 = Unit ) {
                    if (googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate("application")
                    }
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == ComponentActivity.RESULT_OK) {
                            coroutineScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                    if (signInState.isSignInSuccessful) {
                        navController.navigate("complete_registration")
                        viewModel.resetState()
                    }
                }

                SignInScreen(
                    state = signInState,
                    onSignInClick = {
                        coroutineScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }

            composable("complete_registration") {
                val userData = googleAuthUiClient.getSignedInUser()
                val viewModel = viewModel<SignInViewModel>()
                val signInState by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = signInState.isSignInSuccessful)  {
                    studentViewModel.isStudentInDb(userData?.emailAddress)
                }

                if (studentState.isAccountInDb) {
                    navController.navigate("application")
                }
                else {
                    userOnEvent(UserEvent.SetEmail(userData?.emailAddress))
                    userOnEvent(UserEvent.SetProfilePicture(userData?.profilePictureUrl))
                    userOnEvent(UserEvent.IsAccountInDb(isAccountInDb = false))

                    CompleteRegistrationScreen(
                        state = studentState,
                        onEvent = userOnEvent ,
                        navigateToHomeScreen = { navController.navigate("application") }
                    )
                }
            }

        }

        navigation(route = "application", startDestination = "home") {

            composable("home") {
                val userData = googleAuthUiClient.getSignedInUser()

                LaunchedEffect(key1 = true){
                    coroutineScope.launch {
                        userData?.emailAddress?.let { it1 -> studentViewModel?.findByEmail(it1) }
                    }
                }
                HomeNavigationScreen(
                    studentViewModel = studentViewModel,
                    examViewModel = examViewModel,
                    googleAuthUiClient = googleAuthUiClient,
                    navController = navController,
                    pavilionViewModel = pavilionViewModel
                )
            }
        }
    }
    
}