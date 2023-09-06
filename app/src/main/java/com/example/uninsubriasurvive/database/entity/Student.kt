package com.example.uninsubriasurvive.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.uninsubriasurvive.database.utility.ListExamConverter
import com.example.uninsubriasurvive.database.utility.ListExamWithDateConverter

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val matricola: String,
    val email: String,
    val profilePictureUrl: String?,
    val interested: List<ExamWithDate> = ArrayList<ExamWithDate>(),
    val maybe: List<Exam> = ArrayList<Exam>(),
    val notInterested: List<Exam> = ArrayList<Exam>(),
)

