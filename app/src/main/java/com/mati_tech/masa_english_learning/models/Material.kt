package com.mati_tech.masa_english_learning.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "materials")
class Material(
    @ColumnInfo(name = "filename") var filename: String,  // pdf, image, etc.
    @ColumnInfo(name = "filedescription") var filedescription: String,
    @ColumnInfo(name = "filePath") var filePath: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
