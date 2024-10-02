package com.mati_tech.masa_english_learning.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.mati_tech.masa_english_learning.userRepository.UserRepository
import com.mati_tech.masa_english_learning.models.Student
import com.mati_tech.masa_english_learning.models.Teacher

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(application)

    fun insertTeacher(teacher: Teacher) {
        repository.insertTeacher(teacher)
    }

    fun insertStudent(student: Student) {
        repository.insertStudent(student)
    }

    fun getTeacherByEmail(email: String): LiveData<Teacher> {
        return repository.getTeacherByEmail(email)
    }

    fun getStudentByEmail(email: String): LiveData<Student> {
        return repository.getStudentByEmail(email)
    }
}