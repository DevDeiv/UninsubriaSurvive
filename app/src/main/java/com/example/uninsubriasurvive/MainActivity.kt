package com.example.uninsubriasurvive

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Lifecycling
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.uninsubriasurvive.database.Db
import com.example.uninsubriasurvive.database.utility.ListExamConverter
import com.example.uninsubriasurvive.modelview.model.StudentState
import com.example.uninsubriasurvive.modelview.model.UserViewModel
import com.example.uninsubriasurvive.modelview.view.HomeScreen
import com.example.uninsubriasurvive.modelview.view.SignInScreen
import com.example.uninsubriasurvive.navigation.NavGraph
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import com.example.uninsubriasurvive.sign_in.SignInViewModel
import com.example.uninsubriasurvive.ui.theme.AppTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

//    private val db by lazy{
//        Room.databaseBuilder(
//            applicationContext,
//            Db::class.java,
//            "InsubriaSurvive.db"
//        ).fallbackToDestructiveMigration()
//            .build()
//    }


    private val db by lazy {
        Db.getDatabase(applicationContext)
    }
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val userViewModel by viewModels<UserViewModel> (
        factoryProducer = {
            object : ViewModelProvider. Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(dao = db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED


            AppTheme {
                val state by userViewModel.state.collectAsState()
                val navController = rememberNavController()
                Surface {
                    NavGraph(
                        navController = navController,
                        googleAuthUiClient = googleAuthUiClient,
                        applicationContext = context,
                        userViewModel =userViewModel,
                        onEvent = userViewModel::onEvent,
                        state = state
                    )

//                    NavHost(navController = navController, startDestination = "sign_in") {
//                        composable("sign_in") {
//
//                            val viewModel = viewModel<SignInViewModel>()
//                            val state by viewModel.state.collectAsStateWithLifecycle()
//
//                            LaunchedEffect(key1 = Unit ) {
//                                if (googleAuthUiClient.getSignedInUser() != null) {
//                                    navController.navigate("home")
//                                }
//                            }
//
//                            val launcher = rememberLauncherForActivityResult(
//                                contract = ActivityResultContracts.StartIntentSenderForResult(),
//                                onResult = { result ->
//                                    if (result.resultCode == RESULT_OK) {
//                                        lifecycleScope.launch {
//                                            val signInResult = googleAuthUiClient.signInWithIntent(
//                                                intent = result.data ?: return@launch
//                                            )
//                                            viewModel.onSignInResult(signInResult)
//                                        }
//                                    }
//                                }
//                            )
//
//                            LaunchedEffect(key1 = state.isSignInSuccessful) {
//                                if (state.isSignInSuccessful) {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Sign in successful",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                    navController.navigate("home")
//                                    viewModel.resetState()
//
//                                }
//                            }
//                           SignInScreen(
//                               state = state,
//                               onSignInClick = {
//                                   lifecycleScope.launch {
//                                       val signInIntentSender = googleAuthUiClient.signIn()
//                                       launcher.launch(
//                                           IntentSenderRequest.Builder(
//                                               signInIntentSender ?: return@launch
//                                           ).build()
//
//                                       )
//                                   }
//                               }
//                           )
//                        }
//
//                        composable("home") {
//
//                            HomeScreen(
//                                userData = googleAuthUiClient.getSignedInUser(),
//                                onSignOut = {
//                                    lifecycleScope.launch {
//                                        googleAuthUiClient.signOut()
//                                        Toast.makeText(
//                                            context,
//                                            "Signed Out",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//
//                                        navController.popBackStack()
//                                    }
//                                }
//                            )
//
//
//                        }
//                    }
                }
            }
        }
    }
}




