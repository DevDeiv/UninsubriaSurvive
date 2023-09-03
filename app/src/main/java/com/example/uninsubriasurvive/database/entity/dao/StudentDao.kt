package com.example.uninsubriasurvive.database.entity.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.uninsubriasurvive.database.entity.Student

@Dao
interface StudentDao {

    @Upsert
    suspend fun upsertStudent(student: Student)

    @Delete
    suspend fun deleteUser(student: Student)

    @Query("SELECT * FROM student WHERE emailAddress = :emailAddress")
    suspend fun findByEmail(emailAddress: String): Student
}