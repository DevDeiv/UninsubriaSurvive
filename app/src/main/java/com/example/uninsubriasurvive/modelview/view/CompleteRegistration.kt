package com.example.uninsubriasurvive.modelview.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import com.example.uninsubriasurvive.modelview.model.student.UserEvent
import com.example.uninsubriasurvive.modelview.view.utility.CustomTextField
import com.example.uninsubriasurvive.modelview.view.utility.LoginButton

@Composable
fun CompleteRegistrationScreen(
    state: StudentState,
    onEvent: (UserEvent) -> Unit,
    navigateToHomeScreen: () -> Unit
) {

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        CustomTextField(
            enteredText = state.firstName,
            text = "First Name",
            onValueChange = {onEvent(UserEvent.SetFirstName(it))},
            isValid = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            enteredText = state.lastName,
            text = "Last Name",
            onValueChange = {onEvent(UserEvent.SetLastName(it))},
            isValid = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            enteredText = state.matricola,
            text = "Matricola",
            onValueChange = {onEvent(UserEvent.SetMatricola(it))},
            isValid = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        LoginButton(
            text = "COMPLETA LA REGISTRAZIONE",
            onclick = {
                onEvent(UserEvent.SaveStudent)
                navigateToHomeScreen()
            }
        )



    }

}