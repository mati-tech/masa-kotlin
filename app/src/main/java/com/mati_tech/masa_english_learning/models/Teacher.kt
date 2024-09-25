package com.mati_tech.masa_english_learning.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teacher")
class Teacher {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    lateinit var name: String
    lateinit var lastName: String
    var age: Int = 0
    lateinit var email: String
    lateinit var subject: String // Constructor, getters, and setters
}