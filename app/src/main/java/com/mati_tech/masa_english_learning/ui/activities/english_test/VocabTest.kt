package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R

class VocabTest : AppCompatActivity() {
    private var testLevel: String? = null
    private var testType: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_vocab_test)

        testLevel = intent.getStringExtra("TEST_LEVEL")
        testType = intent.getStringExtra("TEST_TYPE")

        Toast.makeText(this, testLevel + testType, Toast.LENGTH_SHORT).show()
    }
}