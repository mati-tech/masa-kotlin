package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R


class english_test_levels : AppCompatActivity() {
    lateinit var textViewA1: TextView
    lateinit var textViewA2: TextView
    lateinit var textViewB1: TextView
    lateinit var textViewB2: TextView
    lateinit var textViewC1: TextView
    lateinit var textViewC2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_english_test_levels)

        textViewA1 = findViewById<TextView>(R.id.english_level_a1)
        textViewA2 = findViewById<TextView>(R.id.english_level_a2)
        textViewB1 = findViewById<TextView>(R.id.english_level_b1)
        textViewB2 = findViewById<TextView>(R.id.english_level_b2)
        textViewC1 = findViewById<TextView>(R.id.english_level_c1)
        textViewC2 = findViewById<TextView>(R.id.english_level_c2)

        textViewA1.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "A1")
            startActivity(intent)
        })
        textViewA2.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "A2")
            startActivity(intent)
        })
        textViewB1.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "B1")
            startActivity(intent)
        })
        textViewB2.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "B2")
            startActivity(intent)
        })
        textViewC1.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "C1")
            startActivity(intent)
        })
        textViewC2.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@english_test_levels,
                englishTestType::class.java
            )
            intent.putExtra("SELECTED_LEVEL", "C2")
            startActivity(intent)
        })
    }
}