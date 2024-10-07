package com.mati_tech.masa_english_learning.ui.activities.vocabulary

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R

class vocabDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vocab_detail)

        // Assume you have retrieved the category name from the intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        if (categoryName != null) {
            // Construct the resource identifier for the array of words

            val resourceId = resources.getIdentifier(categoryName, "array", packageName)

            // Check if the resource exists
            if (resourceId != 0) {
                // Retrieve the array of words using the resource identifier
                val wordsArray = resources.getStringArray(resourceId)

                // Display the words array in the ListView
                val listView = findViewById<ListView>(R.id.listview_vocab_detail)
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, wordsArray)
                listView.adapter = adapter
            } else {
                // Show an error message if the resource could not be found
                Toast.makeText(
                    this,
                    "Words not found for category: $categoryName",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Show an error message if the category name is null
            Toast.makeText(this, "No category name provided", Toast.LENGTH_SHORT).show()
        }


    }
}