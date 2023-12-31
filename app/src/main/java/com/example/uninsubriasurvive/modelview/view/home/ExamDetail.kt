package com.example.uninsubriasurvive.modelview.view.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.database.entity.ExamWithDates
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.modelview.model.student.UserEvent

@Composable
fun ExamDetailsScreen(
    navHomeController: NavController,
    examViewModel: ExamViewModel,
    studentViewModel: StudentViewModel
) {
    val onEvent = studentViewModel::onEvent
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
            items(examState.exam!!.dates) { item: Dates ->

                ShowExamWithDates(
                    exam = examState.exam!!.exam,
                    dates = item,
                    onEvent = onEvent
                )



            }
        }
    }

}

@Composable
fun ShowExamWithDates(
    exam: Exam,
    dates: Dates,
    onEvent: (UserEvent) -> Unit
) {

    val context = LocalContext.current
    Card (
       backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = CircleShape.copy(CornerSize(12.dp))

        ){

        Row(modifier = Modifier) {
            Column(
                modifier = Modifier.padding(26.dp)
            ) {
                Text(
                    text = exam.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text(
                        text = "Modalita': ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = exam.mode,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Row {
                    Text(
                        text = "CFU: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = exam.credits.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Row {
                    Text(
                        text = "Data: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = dates.date,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ora: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = dates.time,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }


            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 22.dp, horizontal = 6.dp).size(30.dp)
        ) {
            Button(onClick = {
                onEvent(UserEvent.AddInterestedExam(ExamWithDate(exam,dates)))
                Toast.makeText(
                    context,
                    "Prenotazione effettuata'",
                    Toast.LENGTH_SHORT
                ).show()
            } )  {
               Text(
                   text = "PRENOTA",
                   style = MaterialTheme.typography.titleMedium.copy(
                       fontSize = 12.sp
                   )
               )
            }
        }

    }

}