package com.example.uninsubriasurvive.database.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uninsubriasurvive.database.entity.Pavilion

@Dao
interface PavilionDao {

    @Insert
    fun insertAllPavilion(pavilionList: List<Pavilion>)

    @Query("SELECT * FROM pavilion")
    fun getAllPavilion(): List<Pavilion>
}