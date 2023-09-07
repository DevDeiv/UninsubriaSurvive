package com.example.uninsubriasurvive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.Pavilion
import com.example.uninsubriasurvive.database.entity.Student
import com.example.uninsubriasurvive.database.entity.dao.DatesDao
import com.example.uninsubriasurvive.database.entity.dao.ExamDao
import com.example.uninsubriasurvive.database.entity.dao.PavilionDao
import com.example.uninsubriasurvive.database.entity.dao.StudentDao
import com.example.uninsubriasurvive.database.prepopolamento.PavilionData
import com.example.uninsubriasurvive.database.utility.ListExamConverter
import com.example.uninsubriasurvive.database.utility.ListExamWithDateConverter
import com.example.uninsubriasurvive.database.utility.ListTimelineConverter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        Student::class,
        Exam::class,
        Dates::class,
        Pavilion::class
    ],
    version = 15
)
@TypeConverters(
    ListExamConverter::class,
    ListExamWithDateConverter::class,
    ListTimelineConverter::class
)

abstract class Db: RoomDatabase() {

    abstract val dao: StudentDao
    abstract val examDao: ExamDao
    abstract val pavilionDao: PavilionDao
    abstract val datesDao: DatesDao


    companion object {
        @Volatile
        private var INSTANCE: Db? = null

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            GlobalScope.launch(Dispatchers.IO) {
                val database = INSTANCE
                println("Dentro il database")

                if (database != null) {


                    val examDao = database.examDao
                    val dateDao = database.datesDao
                    val pavilionDao = database.pavilionDao

                    val analisi = Exam( null,"Analisi", 9,"Scritto")
                    val pdm = Exam( null,"PDM", 9,"Scritto")
                    val programmazione = Exam( null,"Programmazione", 9,"Scritto")
                    val logica = Exam( null,"Logica", 9,"Scritto")
                    val algoritmi = Exam( null,"Algoritmi", 9,"Scritto + Orale")
                    val laboratorioA = Exam( null,"Laboratorio A", 9,"Scritto")
                    val sistemiEReti = Exam( null,"Sistemi e reti", 9,"Scritto")

                    val esameId1 = examDao.insertExam(analisi).toInt()
                    val esameId2 = examDao.insertExam(pdm).toInt()
                    val esameId3 = examDao.insertExam(programmazione).toInt()
                    val esameId4 = examDao.insertExam(logica).toInt()
                    val esameId5 = examDao.insertExam(algoritmi).toInt()
                    val esameId6 = examDao.insertExam(laboratorioA).toInt()
                    val esameId7 = examDao.insertExam(sistemiEReti).toInt()


                    val datePerEsame1 = listOf(
                        Dates(null, "2023-09-10", "09:00", esameId1),
                        Dates(null, "2023-09-22", "14:30", esameId1)
                    )

                    val datePerEsame2 = listOf(
                        Dates(null, "2023-09-05", "11:00", esameId2),
                        Dates(null, "2023-09-20", "16:45", esameId2)
                    )
                    val datePerEsame3 = listOf(
                        Dates(null, "2023-09-08", "11:00", esameId3),
                        Dates(null, "2023-09-16", "16:45", esameId3)
                    )
                    val datePerEsame4 = listOf(
                        Dates(null, "2023-09-15", "11:00", esameId4),
                        Dates(null, "2023-09-20", "16:45", esameId4)
                    )
                    val datePerEsame5 = listOf(
                        Dates(null, "2023-09-10", "11:00", esameId5),
                        Dates(null, "2023-09-18", "16:45", esameId5)
                    )
                    val datePerEsame6 = listOf(
                        Dates(null, "2023-09-15", "11:00", esameId6),
                        Dates(null, "2023-09-20", "16:45", esameId6)
                    )
                    val datePerEsame7 = listOf(
                        Dates(null, "2023-09-03", "11:00", esameId7),
                        Dates(null, "2023-09-12", "16:45", esameId7)
                    )


                    dateDao.insertDates(datePerEsame1)
                    dateDao.insertDates(datePerEsame2)
                    dateDao.insertDates(datePerEsame3)
                    dateDao.insertDates(datePerEsame4)
                    dateDao.insertDates(datePerEsame5)
                    dateDao.insertDates(datePerEsame6)
                    dateDao.insertDates(datePerEsame7)

                    val pavilionToInsert = PavilionData
                    pavilionDao.insertAllPavilion(pavilionToInsert.pavilions)

                }
            }

        }
    }
        fun getDatabase(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    "InsubriaSurvive.db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .addCallback(databaseCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



