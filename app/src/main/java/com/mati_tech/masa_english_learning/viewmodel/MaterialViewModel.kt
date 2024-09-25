package com.mati_tech.masa_english_learning.viewmodel

import MaterialRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.mati_tech.masa_english_learning.models.Material

class MaterialViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MaterialRepository

    private val allMaterials: LiveData<List<Material>>

    init {
        repository = MaterialRepository(application)
        allMaterials = repository.getAllMaterials()
    }

    fun insert(material: Material?) {
        repository.insert(material!!)
    }

    fun delete(material: Material?) {
        repository.delete(material!!)
    }

    fun getAllMaterials(): LiveData<List<Material>> {
        return allMaterials
    }
}