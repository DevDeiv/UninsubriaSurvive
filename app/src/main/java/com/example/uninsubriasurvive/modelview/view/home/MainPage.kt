package com.example.uninsubriasurvive.modelview.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uninsubriasurvive.modelview.model.student.StudentState

@Composable
fun MainPageScreen(
    navController: NavController,
    studentState: StudentState
) {

    Column (
        modifier = Modifier.fillMaxSize().padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Benvenuto ${studentState.firstName} all'interno INSUBRIA SURVIVE!!",
            color = Color.Black,
            textAlign = TextAlign.Center
        )




    }

}