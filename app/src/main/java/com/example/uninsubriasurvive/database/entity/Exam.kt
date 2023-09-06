package com.example.uninsubriasurvive.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val examId: Int? = 0,
    val name: String,
    val credits: Int,
    val mode: String
)
@Entity
data class Dates (
    @PrimaryKey(autoGenerate = true)
    val dateId: Int? = 0,
    val date: String,
    val time: String,
    val dateExamId: Int
)

data class ExamWithDates(
    @Embedded val exam: Exam,
    @Relation(
        parentColumn = "examId",
        entityColumn = "dateExamId"
    )
    val dates: List<Dates>
)

data class ExamWithDate(
    val exam: Exam,
    val date: Dates
)


