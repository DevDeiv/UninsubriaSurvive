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
import com.example.uninsubriasurvive.modelview.view.HomeScreen
import com.example.uninsubriasurvive.modelview.view.SignInScreen
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import com.example.uninsubriasurvive.sign_in.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context
) {
    val coroutineScope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = "sign_in") {

        composable("sign_in") {

            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

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

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("home")
                    viewModel.resetState()

                }
            }
            SignInScreen(
                state = state,
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

        composable("home") {

            HomeScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            applicationContext,
                            "Signed Out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.popBackStack()
                    }
                }
            )


        }
    }
    
}