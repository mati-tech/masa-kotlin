package com.mati_tech.masa_english_learning.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mati_tech.masa_english_learning.models.Material
import com.mati_tech.masa_english_learning.models.Student
import com.mati_tech.masa_english_learning.models.Teacher
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile

@Database(entities = [Student::class, Teacher::class, Material::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
    abstract fun materialDao(): MaterialDao

    companion object {
        @Volatile
        private lateinit var instance: AppDatabase
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS
        )

        fun getInstance(context: Context): AppDatabase {
            return instance
        }

    }
}