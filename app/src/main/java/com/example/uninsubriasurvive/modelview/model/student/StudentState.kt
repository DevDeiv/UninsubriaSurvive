package com.example.uninsubriasurvive.modelview.model.student

import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.database.entity.ExamWithDates

data class StudentState(
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = "",
    val matricola: String = "",
    val emailAddress: String = "",
    val profilePicture: String? = "",
    val interested: List<ExamWithDate> = ArrayList<ExamWithDate>(),
    val maybe:  List<Exam> = ArrayList<Exam>(),
    val notInterested:  List<Exam> = ArrayList<Exam>(),
    val isAccountInDb: Boolean = false
)