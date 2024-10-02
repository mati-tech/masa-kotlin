package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R


class EnglishTestType : AppCompatActivity() {
    private lateinit var vocabView: TextView
    private lateinit var selectedLevel: String
    private lateinit var grammarView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_english_test_type)

        vocabView = findViewById(R.id.vocab_test)
        grammarView = findViewById(R.id.grammer_test)

        selectedLevel = intent.getStringExtra("SELECTED_LEVEL").toString()

        vocabView.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@EnglishTestType,
                TestDetails::class.java
            )
            intent.putExtra("TEST_LEVEL", selectedLevel)
            intent.putExtra("TEST_TYPE", "VOCABULARY")
            startActivity(intent)
        })
        grammarView.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@EnglishTestType,
                TestDetails::class.java
            )
            intent.putExtra("TEST_LEVEL", selectedLevel)
            intent.putExtra("TEST_TYPE", "GRAMMAR")
            startActivity(intent)
        })
    }
}