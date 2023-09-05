package com.example.uninsubriasurvive.modelview.model.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDates
import com.example.uninsubriasurvive.database.entity.dao.ExamDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamViewModel(
    private val dao: ExamDao
): ViewModel(){

    private val _state = MutableStateFlow(ExamState())
    val state = _state.asStateFlow()

    fun onEvent(event: ExamEvent) {
        when(event) {
            ExamEvent.GetAllWithDates -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        examWithDates = dao.getExamWithDates()
                    ) }
                }
            }

            is ExamEvent.GetExamWithDateById -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        exam =  dao.findById(event.examId)
                    ) }
                }
            }
        }
    }
}