package com.example.uninsubriasurvive.database.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.uninsubriasurvive.database.entity.Dates

@Dao
interface DatesDao {

    @Insert
    suspend fun insertDates(date: List<Dates>)
}