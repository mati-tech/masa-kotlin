package com.mati_tech.masa_english_learning.userRepository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mati_tech.masa_english_learning.db.AppDatabase
import com.mati_tech.masa_english_learning.db.TeacherDao

import com.mati_tech.masa_english_learning.db.StudentDao
import com.mati_tech.masa_english_learning.models.Student
import com.mati_tech.masa_english_learning.models.Teacher

class UserRepository(application: Application) {
    private val teacherDao: TeacherDao
    private val studentDao: StudentDao

    init {
        val db = AppDatabase.getInstance(application)
        teacherDao = db.teacherDao()
        studentDao = db.studentDao()
    }

    fun insertTeacher(teacher: Teacher) {
        AppDatabase.databaseWriteExecutor.execute {
            teacherDao.insert(teacher)
        }
    }

    fun insertStudent(student: Student) {
        AppDatabase.databaseWriteExecutor.execute {
            studentDao.insert(student)
        }
    }

    fun getTeacherByEmail(email: String): LiveData<Teacher> {
        return teacherDao.getTeacherByEmail(email)
    }

    fun getStudentByEmail(email: String): LiveData<Student> {
        return studentDao.getStudentByEmail(email)
    }
}