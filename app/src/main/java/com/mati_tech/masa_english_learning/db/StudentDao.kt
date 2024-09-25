package com.mati_tech.masa_english_learning.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.mati_tech.masa_english_learning.models.Student

@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student)

    @Query("SELECT * FROM student WHERE email = :email LIMIT 1")
    fun getStudentByEmail(email: String?): LiveData<Student?>?
}