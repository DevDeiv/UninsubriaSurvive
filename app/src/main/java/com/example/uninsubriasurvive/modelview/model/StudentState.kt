package com.example.uninsubriasurvive.modelview.model

data class StudentState(
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = "",
    val serialNumber: String = "",
    val eMail: String = "",
    val password: String = "",
    val isAddingAccount: Boolean = false
)