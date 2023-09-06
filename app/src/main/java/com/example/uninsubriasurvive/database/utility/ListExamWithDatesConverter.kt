package com.example.uninsubriasurvive.database.utility

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.database.entity.ExamWithDates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListExamWithDateConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<ExamWithDate> ?{
        val listType = object : TypeToken<List<ExamWithDate>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<ExamWithDate>?): String? {
        return gson.toJson(list)
    }
}