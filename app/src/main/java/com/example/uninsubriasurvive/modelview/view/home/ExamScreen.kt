package com.example.uninsubriasurvive.modelview.view.home

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDates
import com.example.uninsubriasurvive.modelview.model.exam.ExamState
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.modelview.model.student.UserEvent
import com.example.uninsubriasurvive.modelview.view.utility.RadioButtonSelection
import com.google.firebase.firestore.auth.User

@Composable
fun ExamScreen(
    navHomeController: NavController,
    examViewModel: ExamViewModel,
    studentViewModel: StudentViewModel
) {
    val studentState by studentViewModel.state.collectAsState()
    val examState by examViewModel.state.collectAsState()
    val onEvent = studentViewModel::onEvent


    Column (
        modifier = Modifier
            .padding(vertical = 26.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn(

        ){
            items(examState.examWithDates) { item: ExamWithDates ->
                ShowExam(
                    exam = item.exam,
                    goToDetails = {navHomeController.navigate("exam_details/${item.exam.examId.toString()}")},
                    onEvent = onEvent,
                    studentState = studentState,
                )


            }
        }
    }

}

@Composable
fun ShowExam(
    exam: Exam,
    goToDetails: () -> Unit,
    onEvent: (UserEvent) -> Unit,
    studentState: StudentState,

) {

    val context = LocalContext.current
    var showDialogMaybeExams by remember {
        mutableStateOf(false)
    }
    var showDialogNotInterestedExams by remember {
        mutableStateOf(false)
    }

    if (showDialogMaybeExams) {
        ShowDialogForDeleteExamsReservationAndAddMaybeExam(
            onEvent = onEvent,
            onDismissRequest = { showDialogMaybeExams = false },
            exam = exam
        )
    }
    if (showDialogNotInterestedExams) {
        ShowDialogForDeleteExamsReservationAndAddNotInterestedExam(
            onEvent = onEvent,
            onDismissRequest = { showDialogNotInterestedExams = false },
            exam = exam
        )
    }

    Card (
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        shape = CircleShape.copy(all = CornerSize(12.dp))

        ){

        Row(
            modifier = Modifier,


        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = exam.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(
                        text = "MODALITA': ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = exam.mode,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Row {
                    Text(
                        text = "CFU: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = exam.credits.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }


            }
        }
        Row (
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ){
           Column (
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.End,
           ){
               IconButton(onClick = { goToDetails() }) {
                   Icon(
                       imageVector = Icons.Outlined.KeyboardArrowRight,
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.onSurfaceVariant,
                       modifier = Modifier.size(22.dp)
                   )
               }
                   TextButton(
                       onClick = {
                           var check = true
                           studentState.interested.forEach {
                               if (it.exam.examId == exam.examId) {
                                   check = false
                                   showDialogMaybeExams = true
                               }
                           }
                           if (check) {
                               onEvent(UserEvent.AddMaybeInterested(exam))
                               Toast.makeText(
                                   context,
                                   "Esame aggiunto alla lista 'da osservare'",
                                   Toast.LENGTH_SHORT
                               ).show()
                           }

                       }) {
                       Text(
                           text = "IN FORSE",

                       )
                   }
               TextButton(onClick = {
                   var check = true
                   studentState.interested.forEach {
                       if (it.exam.examId == exam.examId){
                           check = false
                           showDialogNotInterestedExams = true
                       }
                   }
                   if (check){
                       onEvent(UserEvent.AddNotInterested(exam))
                       Toast.makeText(
                           context,
                           "Esame aggiunto alla lista 'non interessato'",
                           Toast.LENGTH_SHORT
                       ).show()
                   }
               }) {
                  Text(
                      text = "NON INTERESSATO",
                      color = MaterialTheme.colorScheme.secondaryContainer

                  )
               }
           }
        }

    }
    
}

@Composable
fun ShowDialogForDeleteExamsReservationAndAddMaybeExam(
    onEvent: (UserEvent) -> Unit,
    onDismissRequest: () -> Unit,
    exam: Exam
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
            shape = CircleShape.copy(CornerSize(16.dp))
        ) {
            Column (
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "ATTENZIONE",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Trovata prenotazione per l'esame di ${exam.name}, per continuare e' necessario rimuovere tutte le prenotazioni per ${exam.name}, procedere con la rimozione?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextButton(onClick = { onDismissRequest() }) {
                        Text( text = "ANNULLA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onEvent(UserEvent.AddMaybeInterested(exam))
                        onDismissRequest()
                    }) {
                        Text( text = "RIMUOVI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                }

            }

        }

    }

}

@Composable
fun ShowDialogForDeleteExamsReservationAndAddNotInterestedExam(
    onEvent: (UserEvent) -> Unit,
    onDismissRequest: () -> Unit,
    exam: Exam
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
            shape = CircleShape.copy(CornerSize(16.dp))
        ) {
            Column (
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "ATTENZIONE",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Trovata prenotazione per l'esame di ${exam.name}, per continuare e' necessario rimuovere tutte le prenotazioni per ${exam.name}, procedere con la rimozione?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(
                            text = "ANNULLA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onEvent(UserEvent.AddNotInterested(exam))
                        onDismissRequest()
                    }) {
                        Text(
                            text = "RIMUOVI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                }

            }

        }

    }

}



