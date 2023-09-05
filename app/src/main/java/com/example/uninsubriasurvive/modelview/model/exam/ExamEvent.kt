package com.example.uninsubriasurvive.modelview.model.exam

sealed interface ExamEvent {
    object GetAllWithDates: ExamEvent
    data class GetExamWithDateById(val examId: Int): ExamEvent

}