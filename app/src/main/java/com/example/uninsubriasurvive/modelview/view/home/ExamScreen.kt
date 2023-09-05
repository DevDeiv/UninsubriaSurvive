package com.example.uninsubriasurvive.modelview.view.home

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDates
import com.example.uninsubriasurvive.modelview.model.exam.ExamState
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel

@Composable
fun ExamScreen(
    navHomeController: NavController,
    examViewModel: ExamViewModel,
    studentViewModel: StudentViewModel
) {
    val examEvent = examViewModel::onEvent
    val examState by examViewModel.state.collectAsState()


    Column (
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        LazyColumn(

        ){
            items(examState.examWithDates) { item: ExamWithDates ->
                ShowExam(
                    exam = item.exam,
                    goToDetails = {navHomeController.navigate("exam_details/${item.exam.examId.toString()}")}
                )
            }
        }
    }

}

@Composable
fun ShowExam(
    exam: Exam,
    goToDetails: () -> Unit
) {
    Card (
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),

        ){

        Row(modifier = Modifier) {
            Column(
                modifier = Modifier.padding(26.dp)
            ) {
                Text(
                    text = exam.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text(
                        text = "Modalita': ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = exam.mode,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Row {
                    Text(
                        text = "CFU: ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = exam.credits.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }


            }
        }
        Row (
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(18.dp)
        ){
            IconButton(onClick = { goToDetails() }) {
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
            }
        }

    }
    
}



