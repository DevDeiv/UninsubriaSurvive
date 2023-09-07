package com.example.uninsubriasurvive.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pavilion(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String,
    val address: String,
    val timeline: List<String> = ArrayList()
)
