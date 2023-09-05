package com.example.uninsubriasurvive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uninsubriasurvive.database.entity.Dates
import com.example.uninsubriasurvive.database.entity.Exam
import com.example.uninsubriasurvive.database.entity.Student
import com.example.uninsubriasurvive.database.entity.dao.DatesDao
import com.example.uninsubriasurvive.database.entity.dao.ExamDao
import com.example.uninsubriasurvive.database.entity.dao.StudentDao
import com.example.uninsubriasurvive.database.utility.ListExamConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        Student::class,
        Exam::class,
        Dates::class
    ],
    version = 9
)
@TypeConverters(ListExamConverter::class)
abstract class Db: RoomDatabase() {

    abstract val dao: StudentDao
    abstract val examDao: ExamDao
    abstract val datesDao: DatesDao

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
            GlobalScope.launch(Dispatchers.IO) {
                val database = INSTANCE

                if (database != null) {

                    println("Dentro il database")
                    val examDao = database.examDao
                    val dateDao = database.datesDao

                    val esame1 = Exam( null,"Analisi", 9,"Scritto")
                    val esame2 = Exam( null,"PDM", 9,"Scritto")

                    val esameId1 = examDao.insertExam(esame1).toInt()
                    val esameId2 = examDao.insertExam(esame2).toInt()

                    val datePerEsame1 = listOf(
                        Dates(0, "2023-09-10", "09:00", esameId1),
                        Dates(0, "2023-09-12", "14:30", esameId1)
                    )

                    val datePerEsame2 = listOf(
                        Dates(0, "2023-09-15", "11:00", esameId2),
                        Dates(0, "2023-09-20", "16:45", esameId2)
                    )

                    dateDao.insertDates(datePerEsame1)
                    dateDao.insertDates(datePerEsame2)
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
                    .addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch(Dispatchers.IO) {
                                val database = INSTANCE
                                println("Dentro il database")

                                if (database != null) {


                                    val examDao = database.examDao
                                    val dateDao = database.datesDao

                                    val esame1 = Exam( null,"Analisi", 9,"Scritto")
                                    val esame2 = Exam( null,"PDM", 9,"Scritto")
                                    val esame3 = Exam( null,"Programmazione", 9,"Scritto")
                                    val esame4 = Exam( null,"Logica", 9,"Scritto")

                                    val esameId1 = examDao.insertExam(esame1).toInt()
                                    val esameId2 = examDao.insertExam(esame2).toInt()
                                    val esameId3 = examDao.insertExam(esame3).toInt()
                                    val esameId4 = examDao.insertExam(esame4).toInt()


                                    val datePerEsame1 = listOf(
                                        Dates(null, "2023-09-10", "09:00", esameId1),
                                        Dates(null, "2023-09-12", "14:30", esameId1)
                                    )

                                    val datePerEsame2 = listOf(
                                        Dates(null, "2023-09-15", "11:00", esameId2),
                                        Dates(null, "2023-09-20", "16:45", esameId2)
                                    )
                                    val datePerEsame3 = listOf(
                                        Dates(null, "2023-09-15", "11:00", esameId3),
                                        Dates(null, "2023-09-20", "16:45", esameId3)
                                    )
                                    val datePerEsame4 = listOf(
                                        Dates(null, "2023-09-15", "11:00", esameId4),
                                        Dates(null, "2023-09-20", "16:45", esameId4)
                                    )

                                    dateDao.insertDates(datePerEsame1)
                                    dateDao.insertDates(datePerEsame2)
                                    dateDao.insertDates(datePerEsame3)
                                    dateDao.insertDates(datePerEsame4)
                                }
                            }

                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
