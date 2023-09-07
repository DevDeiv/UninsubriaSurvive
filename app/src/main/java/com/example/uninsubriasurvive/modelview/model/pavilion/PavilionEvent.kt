package com.example.uninsubriasurvive.modelview.model.pavilion

sealed interface PavilionEvent {
    object GetAll: PavilionEvent
}