package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R


class englishTestType : AppCompatActivity() {
    lateinit var vocabView: TextView
    private var selectedLevel: String? = null
    lateinit var grammerView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_english_test_type)

        vocabView = findViewById<TextView>(R.id.vocab_test)
        grammerView = findViewById<TextView>(R.id.grammer_test)

        selectedLevel = intent.getStringExtra("SELECTED_LEVEL")

        vocabView.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@englishTestType,
                testDetails::class.java
            )
            intent.putExtra("TEST_LEVEL", selectedLevel)
            intent.putExtra("TEST_TYPE", "VOCABULARY")
            startActivity(intent)
        })
        grammerView.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@englishTestType,
                testDetails::class.java
            )
            intent.putExtra("TEST_LEVEL", selectedLevel)
            intent.putExtra("TEST_TYPE", "GRAMMAR")
            startActivity(intent)
        })
    }
}