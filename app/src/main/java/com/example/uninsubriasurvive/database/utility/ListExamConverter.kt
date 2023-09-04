package com.example.uninsubriasurvive.database.utility

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.uninsubriasurvive.database.entity.Exam
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListExamConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<Exam> ?{
        val listType = object : TypeToken<List<Exam>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Exam>?): String? {
        return gson.toJson(list)
    }
}