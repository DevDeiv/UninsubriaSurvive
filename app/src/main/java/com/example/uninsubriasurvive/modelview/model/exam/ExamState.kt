package com.example.uninsubriasurvive.modelview.model.exam

import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDates

data class ExamState(
    val examWithDates: List<ExamWithDates> = listOf(),
    val exam: ExamWithDates? = null,
)