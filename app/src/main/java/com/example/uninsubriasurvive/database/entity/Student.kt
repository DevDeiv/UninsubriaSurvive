package com.example.uninsubriasurvive.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.uninsubriasurvive.database.utility.ListExamConverter

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val matricola: String,
    val email: String,
    val profilePictureUrl: String?,
    @TypeConverters(ListExamConverter::class) val interested: List<Exam>?,
    @TypeConverters(ListExamConverter::class) val maybe: List<Exam>?,
    @TypeConverters(ListExamConverter::class) val notInterested: List<Exam>?,
)

