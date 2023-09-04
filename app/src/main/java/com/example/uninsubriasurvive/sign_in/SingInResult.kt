package com.example.uninsubriasurvive.sign_in

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val emailAddress: String?,
    val profilePictureUrl: String?
)
