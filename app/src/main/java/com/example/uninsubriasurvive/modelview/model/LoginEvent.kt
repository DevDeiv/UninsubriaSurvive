//package com.example.uninsubriasurvive.modelview.model
//
//import com.example.uninsubriasurvive.database.entities.Student
//
//sealed interface LoginEvent {
//
//    object SaveStudent: LoginEvent
//    data class DeleteStudent(val student: Student): LoginEvent
//    data class UpdateStudent(val student: Student): LoginEvent
//
//    object ResetUser: LoginEvent
//    object HideDialog: LoginEvent
//    object ShowDialog: LoginEvent
//
//    data class SetFirstName(val fistName: String): LoginEvent
//    data class SetLastName(val lastName: String): LoginEvent
//    data class SetSerialNumber(val serialNumber: String): LoginEvent
//    data class SetEmail(val eMail: String): LoginEvent
//    data class SetPassword(val password: String): LoginEvent
//}