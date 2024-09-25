package com.mati_tech.masa_english_learning.ui.activities.english_test

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

class testDetails : AppCompatActivity() {


    private var test_level: String? = null
    private var test_type: String? = null
    var textView: TextView? = null

    private var questionsContainer: LinearLayout? = null
    private var submitButton: Button? = null
    private var questionsList: MutableList<Question>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_test_details)

        test_level = intent.getStringExtra("TEST_LEVEL")
        test_type = intent.getStringExtra("TEST_TYPE")
        textView = findViewById<TextView>(R.id.test_title_text_view)
        textView?.setText("English level $test_level $test_type part\n")

        questionsContainer = findViewById<LinearLayout>(R.id.questions_container)
        submitButton = findViewById<Button>(R.id.submit_button)
        questionsList = ArrayList<Question>()


//        Toast.makeText(this, test_level + test_type, Toast.LENGTH_SHORT).show();

        // Load the JSON file and display the test
        val jsonString = loadJSONFromAsset()
        if (jsonString != null) {
            displayTest(jsonString)
        }
        submitButton?.setOnClickListener(View.OnClickListener { evaluateAnswers() })
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
            val tests = jsonObject.getJSONArray(test_level)
            for (i in 0 until tests.length()) {
                val test = tests.getJSONObject(i)
                if (test.getString("type") == test_type) {
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
                        questionsContainer!!.addView(questionTextView)

                        val optionsGroup = RadioGroup(this)
                        for (k in 0 until optionsArray.length()) {
                            val option = optionsArray.getString(k)
                            val optionButton = RadioButton(this)
                            optionButton.text = option
                            optionsGroup.addView(optionButton)
                        }
                        questionsContainer!!.addView(optionsGroup)

                        question.optionsGroup = optionsGroup
                        questionsList!!.add(question)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun evaluateAnswers() {
        var correctAnswersCount = 0
        for (question in questionsList!!) {
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
        textView!!.text = """English level $test_level $test_type part
                            You got $correctAnswersCount out of ${questionsList!!.size} correct!"""
        Toast.makeText(
            this,
            "You got " + correctAnswersCount + " out of " + questionsList!!.size + " correct!",
            Toast.LENGTH_LONG
        ).show()
    }
}