package com.example.uninsubriasurvive.modelview.model.pavilion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uninsubriasurvive.database.entity.dao.PavilionDao
import com.example.uninsubriasurvive.modelview.model.student.StudentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PavilionViewModel (
    private val dao: PavilionDao
): ViewModel() {

    private val _state = MutableStateFlow(PavilionState())

    val state = _state.asStateFlow()


    fun onEvent(event: PavilionEvent) {
        when(event) {
            PavilionEvent.GetAll -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        pavilions = dao.getAllPavilion()
                    ) }
                }
            }
        }
    }
}