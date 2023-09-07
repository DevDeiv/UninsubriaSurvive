package com.example.uninsubriasurvive.database.utility

import androidx.room.TypeConverter
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListTimelineConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>): String? {
        return gson.toJson(list)
    }
}