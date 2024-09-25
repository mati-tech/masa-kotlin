package com.mati_tech.masa_english_learning.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mati_tech.masa_english_learning.models.Material


@Dao
interface MaterialDao {
    @Insert
    fun insert(material: Material)

    @Delete
    fun delete(material: Material)

    @get:Query("SELECT * FROM materials")
    val allMaterials: LiveData<List<Material>>
}