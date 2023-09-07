package com.example.uninsubriasurvive.modelview.view.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.modelview.model.student.UserEvent
import com.example.uninsubriasurvive.modelview.view.utility.RadioButtonSelection


@Composable
fun BookletScreen(
    navController: NavController,
    studentViewModel: StudentViewModel
) {

    val studentState by studentViewModel.state.collectAsState()
    val onEvent = studentViewModel::onEvent

    var radioButton: RadioButtonSelection by remember {
        mutableStateOf(RadioButtonSelection.ShowInterested)
    }


    Row (
        modifier = Modifier.padding(top = 22.dp, ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        RadioButton(
            selected = radioButton.filter.equals("interest") ,
            onClick = { radioButton = RadioButtonSelection.ShowInterested },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.secondary
            )
        ) 
        Text(
            text = "Prenotazioni",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        RadioButton(
            selected = radioButton.filter.equals("maybe"),
            onClick = { radioButton = RadioButtonSelection.ShowMaybeInterested },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = "Da osservare",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        RadioButton(
            selected = radioButton.filter.equals("not_interest"),
            onClick = { radioButton = RadioButtonSelection.ShowNotInterested },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = "Scartati",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
    Spacer(modifier = Modifier.height(22.dp))
    Column (
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){



        if (radioButton.filter == RadioButtonSelection.ShowInterested.filter) {
            if (studentState.interested.isEmpty()) {
                Text(text = "Nessuna prenotazione da visualizzare",color = Color.Black)
            }

            LazyColumn() {
                items(studentState.interested) { item: ExamWithDate ->

                    ShowExamWithDatesInBooklet(exam = item.exam , dates = item.date , onEvent = onEvent )

                }
            }

        }
        if (radioButton.filter == RadioButtonSelection.ShowMaybeInterested.filter) {
            if (studentState.maybe.isEmpty()) {
                Text(text = "Nessun esame da visualizzare",color = Color.Black)
            }
            LazyColumn() {
                items(studentState.maybe) { item: Exam ->

                    ShowExamInBooklet(
                        exam = item,
                        onEvent = onEvent,
                        filter = "maybe"
                    )
                }
            }
        }

        if (radioButton.filter == RadioButtonSelection.ShowNotInterested.filter) {
            if (studentState.notInterested.isEmpty()) {
                Text(text = "Nessun esame da visualizzare",color = Color.Black)
            }

            LazyColumn() {
                items(studentState.notInterested) { item: Exam ->
                    print("ESAME $item")

                    ShowExamInBooklet(
                        exam = item,
                        onEvent = onEvent,
                        filter = "notInterest"
                    )

                }
            }

        }
    }
}

@Composable
fun ShowExamInBooklet(
    exam: Exam,
    onEvent: (UserEvent) -> Unit,
    filter: String
) {
    Card (
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
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
                        fontWeight = FontWeight.Bold
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
            }
        }
        Row (
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(18.dp)
        ){
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                IconButton(onClick = {
                    if (filter == "maybe")
                        onEvent(UserEvent.RemoveMaybeInterested(exam))
                    else if (filter == "notInterest")
                        onEvent(UserEvent.RemoveNotInterested(exam))
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

    }

}

@Composable
fun ShowExamWithDatesInBooklet(
    exam: Exam,
    dates: Dates,
    onEvent: (UserEvent) -> Unit
) {

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
            modifier = Modifier.padding(18.dp)
        ) {
            IconButton(onClick = { onEvent(UserEvent.RemoveInterested(ExamWithDate(exam,dates))) } )  {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }

}
