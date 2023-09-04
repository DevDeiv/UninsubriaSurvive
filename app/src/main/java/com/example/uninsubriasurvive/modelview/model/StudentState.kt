package com.example.uninsubriasurvive.modelview.model

data class StudentState(
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = "",
    val matricola: String = "",
    val emailAddress: String = "",
    val profilePicture: String? = "",
    val isAccountInDb: Boolean = true
)