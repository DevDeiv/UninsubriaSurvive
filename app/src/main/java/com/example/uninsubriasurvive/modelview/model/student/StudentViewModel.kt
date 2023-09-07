package com.example.uninsubriasurvive.modelview.model.student

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.ExamWithDate
import com.example.uninsubriasurvive.database.entity.Student
import com.example.uninsubriasurvive.database.entity.dao.StudentDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class StudentViewModel (
    private val dao: StudentDao
): ViewModel() {

    private val _state = MutableStateFlow(StudentState())

    val state = _state.asStateFlow()

    val returnedVal = MutableLiveData<Student>()

    suspend fun findByEmail(emailAddress: String) {
        viewModelScope.launch {
           val student: Student? =  dao.findByEmail(emailAddress)
            student?.let { UserEvent.CopyStudent(it) }?.let { onEvent(it) }
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

    fun buildStudent(): Student {
        val student = Student(
            _state.value.id,
            _state.value.firstName,
            _state.value.lastName,
            _state.value.matricola,
            _state.value.emailAddress,
            _state.value.profilePicture,
            _state.value.interested,
            _state.value.maybe,
            _state.value.notInterested,
        )
        return student
    }

    private fun retrieveMaybeExams() :ArrayList<Exam> {
        val exams = ArrayList<Exam>()
        _state.value.maybe.forEach {
            exams.add(it)
        }
        return exams
    }
    private fun retrieveNotInterestedExam(): ArrayList<Exam> {
        val exams = ArrayList<Exam>()
        _state.value.notInterested.forEach {
            exams.add(it)
        }
        return exams
    }

    private fun retrieveExamsWhitDate(): ArrayList<ExamWithDate> {
        val examsWithDate = ArrayList<ExamWithDate>()
        _state.value.interested.forEach {
            examsWithDate.add(it)
        }
        return examsWithDate
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

                    val student = Student(
                        firstName = firstName,
                        lastName = lastName,
                        matricola = matricola,
                        email = emailAddress,
                        profilePictureUrl = profilePicture,
                        )

                    viewModelScope.launch {
                        dao.upsertStudent(student)
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
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        emailAddress = email,
                        matricola = matricola,
                        profilePicture = profilePictureUrl,
                        interested =  interested,
                        maybe = maybe,
                        notInterested = notInterested
                    ) }
                }
            }

            UserEvent.ResetViewModel -> {
                _state.update { StudentState() }
            }

            is UserEvent.AddInterestedExam -> {

                val exams = retrieveExamsWhitDate()
                exams.add(event.examWithDate)

                val maybeExams = retrieveMaybeExams()
                val notInterestExam = retrieveNotInterestedExam()


                if (_state.value.maybe.contains(event.examWithDate.exam)){
                    maybeExams.remove(event.examWithDate.exam)
                }
                if (_state.value.notInterested.contains(event.examWithDate.exam)){
                    notInterestExam.remove(event.examWithDate.exam)
                }

                _state.update { it.copy(
                    interested = exams,
                    maybe = maybeExams,
                    notInterested = notInterestExam
                ) }

                val student = buildStudent()
                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
            }

            is UserEvent.AddMaybeInterested -> {
                val exams = retrieveMaybeExams()
                exams.add(event.exam)

                val notInterestExam = retrieveNotInterestedExam()
                val interestExams = retrieveExamsWhitDate()

                interestExams.forEach{
                    if (it.exam.examId == event.exam.examId) {
                        interestExams.remove(it)
                    }
                }

                if (_state.value.notInterested.contains(event.exam)) {
                    notInterestExam.remove(event.exam)
                }
                _state.update { it.copy(
                    maybe = exams,
                    notInterested = notInterestExam,
                    interested = interestExams
                ) }

                val student = buildStudent()

                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
                println("QUI ARRRIVO BENE")
            }

            is UserEvent.AddNotInterested -> {
                val exams = retrieveNotInterestedExam()
                exams.add(event.exam)

                val interestExams = retrieveExamsWhitDate()
                interestExams.forEach{
                    if (it.exam.examId == event.exam.examId) {
                        interestExams.remove(it)
                    }
                }

                val maybeExams = retrieveMaybeExams()
                if (_state.value.notInterested.contains(event.exam)) {
                    maybeExams.remove(event.exam)
                }

                _state.update { it.copy(
                    notInterested = exams,
                    maybe = maybeExams,
                    interested = interestExams
                ) }

                val student = buildStudent()
                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
            }

            is UserEvent.RemoveInterested -> {
                val examsWithDate = retrieveExamsWhitDate()
                examsWithDate.remove(event.exam)

                _state.update { it.copy(
                    interested = examsWithDate
                ) }
                val student = buildStudent()
                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
            }

            is UserEvent.RemoveMaybeInterested -> {
                val exams = retrieveMaybeExams()
                exams.remove(event.exam)

                _state.update { it.copy(
                    maybe = exams
                ) }

                val student = buildStudent()
                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
            }

            is UserEvent.RemoveNotInterested -> {
                val exams = retrieveNotInterestedExam()
                exams.remove(event.exam)

                _state.update { it.copy(
                    notInterested = exams
                ) }

                val student = buildStudent()
                viewModelScope.launch {
                    dao.upsertStudent(student)
                }
            }
        }


    }
}