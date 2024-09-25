package com.mati_tech.masa_english_learning.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mati_tech.masa_english_learning.models.Teacher


@Dao
interface TeacherDao {
    @Insert
    fun insert(teacher: Teacher)

    @Query("SELECT * FROM teacher WHERE email = :email LIMIT 1")
    fun getTeacherByEmail(email: String?): LiveData<Teacher?>?
}