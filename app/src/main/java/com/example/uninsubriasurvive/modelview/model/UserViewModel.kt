package com.example.uninsubriasurvive.modelview.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uninsubriasurvive.database.entity.Student
import com.example.uninsubriasurvive.database.entity.dao.StudentDao
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel (
    private val dao: StudentDao
): ViewModel() {

    private val _state = MutableStateFlow(StudentState())

    val state = _state.asStateFlow()

    val returnedVal = MutableLiveData<Student>()

    suspend fun findByEmail(emailAddress: String) {
        viewModelScope.launch {
           val student: Student? =  dao.findByEmail(emailAddress)
            onEvent(UserEvent.CopyStudent(student!!))
        }
    }
    suspend fun deleteStudent(student: Student) {
        viewModelScope.launch {
            dao.deleteStudent(student)
        }
    }

    suspend fun isStudentInDb(emailAddress: String?)  {

        viewModelScope.launch {
            try {
                val student: Student? = emailAddress?.let { dao.findByEmail(it) }
                if (student == null ) {
                    print("LO STUDENTE CCHE NON ESISTE $student" )
                    onEvent(UserEvent.IsAccountInDb(false))
                } else {
                    println("Student $student")
                    onEvent(UserEvent.IsAccountInDb(true))

                }

            } catch (e:Exception) {
                println("Eccezione $e")
                onEvent(UserEvent.IsAccountInDb(false))

            }
        }
    }

    fun onEvent (event: UserEvent) {

        when(event){
            is UserEvent.DeleteStudent -> {
                viewModelScope.launch {
                    dao.deleteStudent(event.student)
                }
            }
            is UserEvent.FindStudent -> {
                viewModelScope.launch {
                    returnedVal.value = dao.findByEmail(event.emailAddress)
                }
            }
             is UserEvent.IsAccountInDb -> {
              _state.update { it.copy(
                  isAccountInDb = event.isAccountInDb
              ) }
            }
            UserEvent.SaveStudent -> {
                if (!_state.value.isAccountInDb) {
                    val firstName = _state.value.firstName
                    val lastName = _state.value.lastName
                    val matricola = _state.value.matricola
                    val emailAddress = _state.value.emailAddress
                    val profilePicture = _state.value.profilePicture
                    if (
                        firstName != null &&
                        lastName != null &&
                        matricola != null &&
                        emailAddress != null
                    ) {
                        val student = Student(
                            firstName = firstName,
                            lastName = lastName,
                            matricola = matricola,
                            email = emailAddress,
                            profilePictureUrl = profilePicture,
                            interested = null,
                            maybe = null,
                            notInterested = null,
                        )

                        viewModelScope.launch {
                            dao.upsertStudent(student)
                        }
                    }
                }
            }
            is UserEvent.SetEmail -> {
                _state.update { it.copy(
                    emailAddress = event.emailAddress!!
                ) }
            }
            is UserEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is UserEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
            is UserEvent.SetMatricola -> {
                _state.update { it.copy(
                    matricola = event.matricola
                ) }
            }

            is UserEvent.SetProfilePicture -> {
                _state.update { it.copy(
                    profilePicture = event.profilePicture
                ) }
            }

            is UserEvent.CopyStudent -> {
                event.student.run {
                    _state.update { it.copy(
                        firstName = firstName,
                        lastName = lastName,
                        emailAddress = email,
                        matricola = matricola,
                        profilePicture = profilePictureUrl
                    ) }
                }
            }
            UserEvent.ResetViewModel -> TODO()
        }

    }
}