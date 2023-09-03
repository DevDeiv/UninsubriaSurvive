package com.example.uninsubriasurvive.modelview.view.utility

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun LoginButton(text: String, onclick: () -> Unit) {
    val configuration = LocalConfiguration.current

    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp


    Button(
        onClick =  onclick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        modifier = Modifier
            .width(width * 0.8.dp)
            .height(45.dp),

        ) {
        Text(text = text)
    }
}
