package com.example.uninsubriasurvive

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.uninsubriasurvive.database.Db
import com.example.uninsubriasurvive.modelview.model.exam.ExamViewModel
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionViewModel
import com.example.uninsubriasurvive.modelview.model.student.StudentViewModel
import com.example.uninsubriasurvive.navigation.NavGraph
import com.example.uninsubriasurvive.sign_in.GoogleAuthUiClient
import com.example.uninsubriasurvive.ui.theme.AppTheme
import com.google.android.gms.auth.api.identity.Identity


class MainActivity : ComponentActivity() {


    private val db by lazy {
        Db.getDatabase(applicationContext)
    }
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val userViewModel by viewModels<StudentViewModel> (
        factoryProducer = {
            object : ViewModelProvider. Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return StudentViewModel(dao = db.dao) as T
                }
            }
        }
    )
    private val examViewModel by viewModels<ExamViewModel> (
        factoryProducer = {
            object : ViewModelProvider. Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExamViewModel(dao = db.examDao) as T
                }
            }
        }
    )
    private val pavilionViewModel by viewModels<PavilionViewModel> (
        factoryProducer = {
            object : ViewModelProvider. Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PavilionViewModel(dao = db.pavilionDao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


            AppTheme {
                Surface {
                    NavGraph(
                        googleAuthUiClient = googleAuthUiClient,
                        applicationContext = context,
                        studentViewModel =userViewModel,
                        examViewModel = examViewModel,
                        pavilionViewModel = pavilionViewModel
                    )
                }
            }
        }
    }
}




