package com.example.uninsubriasurvive.modelview.model.pavilion

import com.example.uninsubriasurvive.database.entity.Pavilion

data class PavilionState(
    val pavilions: List<Pavilion> = ArrayList<Pavilion>()
)
