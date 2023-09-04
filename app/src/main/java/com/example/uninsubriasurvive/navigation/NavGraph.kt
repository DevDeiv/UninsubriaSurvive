package com.example.uninsubriasurvive.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uninsubriasurvive.modelview.model.StudentState
import com.example.uninsubriasurvive.modelview.model.UserEvent
import com.example.uninsubriasurvive.modelview.model.UserViewModel
import com.example.uninsubriasurvive.modelview.view.CompleteRegistrationScreen
import com.example.uninsubriasurvive.modelview.view.HomeScreen
import com.example.uninsubriasurvive.modelview.view.SignInScreen
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import com.example.uninsubriasurvive.sign_in.SignInViewModel
import com.example.uninsubriasurvive.sign_in.UserData
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    userViewModel: UserViewModel,
    onEvent: (UserEvent) -> Unit,
    state: StudentState
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "sign_in") {



        composable("sign_in") {

            val viewModel = viewModel<SignInViewModel>()
            val signInState by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit ) {

                if (googleAuthUiClient.getSignedInUser() != null) {

                    navController.navigate("home")
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
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
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

            LaunchedEffect(key1 = true)  {
                userViewModel.isStudentInDb(userData?.emailAddress)
            }

            if (state.isAccountInDb) {

                navController.navigate("home")
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "User not Saved in db ",
                    Toast.LENGTH_LONG
                ).show()

                onEvent(UserEvent.SetEmail(userData?.emailAddress))
                onEvent(UserEvent.SetProfilePicture(userData?.profilePictureUrl))
                onEvent(UserEvent.IsAccountInDb(isAccountInDb = false))


                CompleteRegistrationScreen(
                    state = state,
                    onEvent = onEvent ,
                    navigateToHomeScreen = { navController.navigate("home") }
                )
            }
        }

        composable("home") {
            val userData = googleAuthUiClient.getSignedInUser()

            LaunchedEffect(key1 = true){
                coroutineScope.launch {
                    userData?.emailAddress?.let { it1 -> userViewModel?.findByEmail(it1) }
                }
            }

            HomeScreen(
                userState = state,
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            applicationContext,
                            "Signed Out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("sign_in")
                    }
                }
            )


        }
    }
    
}