package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R

class GrammerTest : AppCompatActivity() {
    private var testLevel: String? = null
    private var test_type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_grammer_test)

        testLevel = intent.getStringExtra("TEST_LEVEL")
        test_type = intent.getStringExtra("TEST_TYPE")

        Toast.makeText(this, testLevel + test_type, Toast.LENGTH_SHORT).show()
    }
}