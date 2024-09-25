package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R

class vocabTest : AppCompatActivity() {
    private var test_level: String? = null
    private var test_type: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_vocab_test)

        test_level = intent.getStringExtra("TEST_LEVEL")
        test_type = intent.getStringExtra("TEST_TYPE")

        Toast.makeText(this, test_level + test_type, Toast.LENGTH_SHORT).show()
    }
}