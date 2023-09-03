//package com.example.uninsubriasurvive.modelview.model
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.uninsubriasurvive.database.entities.Student
//import com.example.uninsubriasurvive.database.entities.dao.StudentDao
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//class LoginViewModel(
//    private val dao: StudentDao
//): ViewModel() {
//
//    val state = MutableStateFlow(StudentState()) //POTREBBE ESSERE PRIVATO
//
//    fun onEvent(event: LoginEvent) {
//        when(event) {
//            is LoginEvent.DeleteStudent -> {
//               viewModelScope.launch {
////                   dao.deleteStudent(event.student)
//               }
//            }
//            LoginEvent.SaveStudent -> {
//                val firstName = state.value.firstName
//                val lastName = state.value.lastName
//                val serialNumber = state.value.serialNumber
//                val eMail = state.value.eMail
//                val password = state.value.password
//
//                if (firstName.isBlank() ||
//                    lastName.isBlank() ||
//                    serialNumber.isBlank() ||
//                    eMail.isBlank() ||
//                    password.isBlank()) {
//                }
//
//                val student = Student(
//                    firstName = firstName,
//                    lastName = lastName,
//                    serialNumber = serialNumber,
//                    email = eMail,
//                    password = password
//                )
//
//                viewModelScope.launch {
//                    dao.upsertStudent(student)
//                }
//
//            }
//            is LoginEvent.SetEmail -> {
//                state.update { it.copy(
//                    eMail = event.eMail
//                ) }
//            }
//            is LoginEvent.SetFirstName -> {
//                state.update { it.copy(
//                    firstName = event.fistName
//                ) }
//            }
//            is LoginEvent.SetLastName -> {
//                state.update { it.copy(
//                    lastName = event.lastName
//                ) }
//            }
//            is LoginEvent.SetPassword -> {
//                state.update { it.copy(
//                    password = event.password
//                ) }
//            }
//            is LoginEvent.SetSerialNumber -> {
//                state.update { it.copy(
//                    serialNumber = event.serialNumber
//                ) }
//            }
//            is LoginEvent.UpdateStudent -> {
//                viewModelScope.launch {
//                    dao.upsertStudent(event.student)
//                }
//            }
//
//            LoginEvent.HideDialog -> {
//                state.update { it.copy(
//                    isAddingAccount = false
//                ) }
//            }
//            LoginEvent.ShowDialog -> {
//                state.update { it.copy(
//                    isAddingAccount = true
//                ) }
//            }
//
//            LoginEvent.ResetUser -> {
//                state.update { state -> state.copy(
//                    id = null,
//                    eMail = "",
//                    password =  "",
//                    serialNumber = "",
//                    lastName = "",
//                    firstName = ""
//                ) }
//            }
//        }
//    }
//
//}