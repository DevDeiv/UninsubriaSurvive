package com.example.uninsubriasurvive.modelview.view


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.uninsubriasurvive.R
import com.example.uninsubriasurvive.ui.theme.AppTheme
import com.example.uninsubriasurvive.modelview.view.utility.LoginButton
import com.example.uninsubriasurvive.sign_in.SignInState


@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
//    navigateToSignUpScreen: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError ) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            )
        }
    }

    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()

                .padding(vertical = height * 0.05.dp)
        ) {
            Column (
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier.clip(CircleShape),
                    ) {
                    Image(
                        painter = painterResource(id = R.mipmap.logofnale),
                        contentDescription = null,
                        Modifier.size(450.dp),
                        alignment = Alignment.Center

                    )
                }
            }


            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                LoginButton(
                    text = "ACCEDI CON GOOGLE",
                    onclick = onSignInClick
                )

            }
        }
    }
}


@Composable
@Preview
fun mainPreview() {
    AppTheme {
        SignInScreen(
            state = SignInState(),
            onSignInClick = {},

        )
    }

        

    
}