package com.example.uninsubriasurvive.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val matricola: String,
    val emailAddress: String,
    val profilePictureUrl: String?
)
