package com.example.uninsubriasurvive.modelview.model.student

import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.database.entity.Student

sealed interface UserEvent {

    object SaveStudent: UserEvent
    data class IsAccountInDb(val isAccountInDb: Boolean) : UserEvent
    data class DeleteStudent(val student: Student): UserEvent
    data class  FindStudent(val emailAddress: String): UserEvent

    data class SetFirstName(val firstName: String): UserEvent
    data class SetLastName(val lastName: String): UserEvent
    data class SetEmail(val emailAddress:  String?): UserEvent
    data class SetProfilePicture(val profilePicture: String?): UserEvent
    data class SetMatricola(val matricola: String): UserEvent
    data class  CopyStudent(val student: Student): UserEvent
    data class AddInterestedExam(val examWithDate: ExamWithDate): UserEvent
    data class AddMaybeInterested(val exam: Exam): UserEvent
    data class AddNotInterested(val exam: Exam): UserEvent
    data class RemoveInterested(val exam: ExamWithDate): UserEvent
    data class RemoveMaybeInterested(val exam: Exam): UserEvent
    data class RemoveNotInterested(val exam: Exam): UserEvent
    object ResetViewModel: UserEvent

}