package com.mati_tech.masa_english_learning.ui.activities.grammer

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class grammerDetail : AppCompatActivity() {
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grammer_detail)
        textView = findViewById(R.id.grammer_text_main)

        // Retrieve the category name from the intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME")


        // Initialize InputStream and StringBuilder
        var inputStream: InputStream? = null
        val stringBuilder = StringBuilder()


        // Check the category name and open the appropriate raw resource file
        if (categoryName != null) {
            var resourceId = 0
            if (categoryName == "partofspeech") {
                resourceId = R.raw.partofspeech
            } else if (categoryName == "sentencestructure") {
                resourceId = R.raw.sentencestructure
            } else if (categoryName == "clausesandphrases") {
                resourceId = R.raw.clausesandphrases
            } else if (categoryName == "tenses") {
                resourceId = R.raw.tenses
            } else if (categoryName == "sentencetype") {
                resourceId = R.raw.sentencetype
            } else if (categoryName == "othergrammerpart") {
                resourceId = R.raw.othergrammerpart
            }

            // If a valid resource ID is found, open the raw resource file
            if (resourceId != 0) {
                inputStream = resources.openRawResource(resourceId)
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                try {
                    // Read lines from the file and append them to the StringBuilder
                    while ((reader.readLine().also { line = it }) != null) {
                        stringBuilder.append(line).append('\n')
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    try {
                        // Close the input stream
                        if (inputStream != null) {
                            inputStream.close()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else {
                // Show an error message if the resource ID is invalid
                Toast.makeText(this, "Invalid category name: $categoryName", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            // Show an error message if the category name is null
            Toast.makeText(this, "No category name provided", Toast.LENGTH_SHORT).show()
        }


        // Convert the content of the StringBuilder to a string
        val grammarInfo = stringBuilder.toString()
        textView.setText(grammarInfo)

    }
}