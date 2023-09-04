package com.example.uninsubriasurvive.database.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDates

@Dao
interface ExamDao {

    @Insert
    fun insertExam(exam: Exam): Long

    @Transaction
    @Query("SELECT * FROM exam")
    fun getExamWithDates(): List<ExamWithDates>
}