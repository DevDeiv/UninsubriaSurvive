package com.example.uninsubriasurvive.modelview.view

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import com.example.uninsubriasurvive.modelview.model.student.UserEvent
import com.example.uninsubriasurvive.modelview.view.utility.CustomText
import com.example.uninsubriasurvive.modelview.view.utility.CustomTextField
import com.example.uninsubriasurvive.modelview.view.utility.LoginButton

fun isTextValid(text: String): Boolean {

    return text.matches(Regex("[a-zA-Z]+"))
}

fun isSerialNumberValid(text: String): Boolean {

    return text.matches(Regex("[0-9]+"))
}

@Composable
fun CompleteRegistrationScreen(
    state: StudentState,
    onEvent: (UserEvent) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    var isFirstNameValid by remember { mutableStateOf(true) }
    var isLastNameValid by remember { mutableStateOf(true) }
    var isMatricolaValid by remember { mutableStateOf(true) }


    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))
            CustomTextField(
                enteredText = state.firstName,
                text = "First Name",
                onValueChange = { onEvent(UserEvent.SetFirstName(it)) },
                isValid = isTextValid(state.firstName)
            )
            if (!isFirstNameValid) {
                CustomText(
                    text = "Il campo first name deve contenere solo lettere e deve essere composto da piu' di 4 caratteri "
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                enteredText = state.lastName,
                text = "Last Name",
                onValueChange = { onEvent(UserEvent.SetLastName(it)) },
                isValid = isTextValid(state.lastName)
            )
            if (!isLastNameValid) {

                CustomText(
                    text = "Il campo last name deve contenere solo lettere e deve essere composto da piu' di 3 caratteri "
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                enteredText = state.matricola,
                text = "Matricola",
                onValueChange = { onEvent(UserEvent.SetMatricola(it)) },
                isValid = isSerialNumberValid(text = state.matricola)
            )
            if (!isMatricolaValid) {

                CustomText(
                    text = "Il campo serial number deve contenere solo numeri interi e deve essere composto da 5 numeri"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))



            Column (
                 modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LoginButton(
                    text = "COMPLETA LA REGISTRAZIONE",
                    onclick = {
                        if (
                            isTextValid(state.firstName) && state.firstName.length >= 5 &&
                            isTextValid(state.lastName) && state.lastName.length >= 5 &&
                            isSerialNumberValid(state.matricola) && state.matricola.length == 5
                        ) {
                            onEvent(UserEvent.SaveStudent)
                            navigateToHomeScreen()
                        } else {
                            isFirstNameValid = state.firstName.length >= 5
                            isLastNameValid = state.lastName.length >= 5
                            isMatricolaValid = state.matricola.length == 5

                            Toast.makeText(
                                context,
                                "Form Errato",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                LoginButton(
                    text = "Annulla",
                    onclick = { navigateBack() }
                )
            }
        }
    }

}