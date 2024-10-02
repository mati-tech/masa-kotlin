package com.mati_tech.masa_english_learning.ui.activities.english_test

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.models.Question
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TestDetails : AppCompatActivity() {


    private lateinit var testLevel: String
    private lateinit var testType: String
    private lateinit var textView: TextView

    private lateinit var questionsContainer: LinearLayout
    private lateinit var submitButton: Button
    private lateinit var questionsList: MutableList<Question>
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_test_details)

        testLevel = intent.getStringExtra("TEST_LEVEL").toString()
        testType = intent.getStringExtra("TEST_TYPE").toString()
        textView = findViewById(R.id.test_title_text_view)
        textView.text = "English level $testLevel $testType part\n"

        questionsContainer = findViewById(R.id.questions_container)
        submitButton = findViewById(R.id.submit_button)
        questionsList = ArrayList()


//        Toast.makeText(this, test_level + test_type, Toast.LENGTH_SHORT).show();

        // Load the JSON file and display the test
        val jsonString = loadJSONFromAsset()
        if (jsonString != null) {
            displayTest(jsonString)
        }
        submitButton.setOnClickListener { evaluateAnswers() }
    }


    private fun loadJSONFromAsset(): String? {
        val json = StringBuilder()
        try {
            val `is` = resources.openRawResource(R.raw.englishtest)
            val reader = BufferedReader(InputStreamReader(`is`))
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                json.append(line)
            }
            reader.close()
            `is`.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json.toString()
    }

    private fun displayTest(jsonString: String) {
        try {
            val jsonObject = JSONObject(jsonString)
            val tests = jsonObject.getJSONArray(testLevel)
            for (i in 0 until tests.length()) {
                val test = tests.getJSONObject(i)
                if (test.getString("type") == testType) {
                    val questionsArray = test.getJSONArray("questions")
                    for (j in 0 until questionsArray.length()) {
                        val questionObject = questionsArray.getJSONObject(j)
                        val questionText = questionObject.getString("question")
                        val correctAnswer = questionObject.getString("answer")
                        val optionsArray = questionObject.getJSONArray("options")

                        val question: Question = Question()
                        question.questionText = questionText
                        question.correctAnswer = correctAnswer


                        val questionTextView = TextView(this)
                        questionTextView.text = questionText
                        questionTextView.setPadding(0, 15, 0, 15)
                        questionsContainer.addView(questionTextView)

                        val optionsGroup = RadioGroup(this)
                        for (k in 0 until optionsArray.length()) {
                            val option = optionsArray.getString(k)
                            val optionButton = RadioButton(this)
                            optionButton.text = option
                            optionsGroup.addView(optionButton)
                        }
                        questionsContainer.addView(optionsGroup)

                        question.optionsGroup = optionsGroup
                        questionsList.add(question)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun evaluateAnswers() {
        var correctAnswersCount = 0
        for (question in questionsList) {
            val optionsGroup: RadioGroup = question.optionsGroup
            val selectedId = optionsGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedButton = findViewById<RadioButton>(selectedId)
                val selectedAnswer = selectedButton.text.toString()
                if (selectedAnswer == question.correctAnswer) {
                    correctAnswersCount++
                    selectedButton.setTextColor(Color.GREEN)
                } else {
                    selectedButton.setTextColor(Color.RED)
                    for (i in 0 until optionsGroup.childCount) {
                        val button = optionsGroup.getChildAt(i) as RadioButton
                        if (button.text.toString() == question.correctAnswer) {
                            button.setTextColor(Color.GREEN)
                        }
                    }
                }
            }
        }
        textView.text = """English level $testLevel $testType part
                            You got $correctAnswersCount out of ${questionsList.size} correct!"""
        Toast.makeText(
            this,
            "You got " + correctAnswersCount + " out of " + questionsList.size + " correct!",
            Toast.LENGTH_LONG
        ).show()
    }
}