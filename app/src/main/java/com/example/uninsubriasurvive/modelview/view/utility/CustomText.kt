package com.example.uninsubriasurvive.modelview.view.utility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times


    @Composable
    fun CustomTextFieldWithIcon(
        enteredText: String,
        icon: Painter,
        text: String,
        onValueChange: (String) -> Unit,
        isValid: Boolean
    ) {

        val configuration = LocalConfiguration.current

        val width = configuration.screenWidthDp
        val height = configuration.screenHeightDp

        OutlinedTextField(
            value = enteredText,
            onValueChange = onValueChange,
            leadingIcon = { Icon(icon, contentDescription = null) },
            label = { Text(text) },
            isError = enteredText.isNotEmpty() && !isValid,
            singleLine = true,
            modifier = Modifier
                .width(width = width * 0.8.dp)
                .border(
                    BorderStroke(2.dp, color = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,

                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,

                unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,

                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,

                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                errorContainerColor = MaterialTheme.colorScheme.background,
                errorIndicatorColor = MaterialTheme.colorScheme.background,
                errorTextColor = MaterialTheme.colorScheme.error,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary

            ),

            )

    }

    @Composable
    fun CustomTextField(
        enteredText: String,
        text: String,
        onValueChange: (String) -> Unit,
        isValid: Boolean
    ) {

        val configuration = LocalConfiguration.current

        val width = configuration.screenWidthDp
        val height = configuration.screenHeightDp

        OutlinedTextField(
            value = enteredText,
            onValueChange = onValueChange,
            label = { Text(text) },
            isError = enteredText.isNotEmpty() && !isValid,
            singleLine = true,
            modifier = Modifier
                .width(width = width * 0.8.dp)
                .padding(4.dp)
                .border(
                    BorderStroke(2.dp, color = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,

                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,

                unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,

                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,

                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                errorContainerColor = MaterialTheme.colorScheme.background,
                errorIndicatorColor = MaterialTheme.colorScheme.background,
                errorTextColor = MaterialTheme.colorScheme.error,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary
            ),

            )

    }

fun isTextValid(text: String): Boolean {

    return text.matches(Regex("[a-zA-Z]+"))
}
@Composable
fun isSerialNumberValid(text: String): Boolean {

    return text.matches(Regex("[a-zA-Z0-9]+"))
}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Color.Red),
        textAlign = TextAlign.Center
    )

}