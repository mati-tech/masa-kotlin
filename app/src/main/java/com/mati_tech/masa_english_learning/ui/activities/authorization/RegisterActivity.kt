package com.mati_tech.masa_english_learning.ui.activities.authorization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.databaseHelper.DatabaseHelper

class RegisterActivity : AppCompatActivity() {
    private lateinit var regEmail: EditText
    private lateinit var regPass: EditText
    private lateinit var regConfirmPass: EditText
    private lateinit var regEmailStr: String
    private lateinit var regPasswordStr: String
    private lateinit var confirmPassStr: String
    private lateinit var radioBtnTeacher: RadioButton
    private lateinit var role: String
    private lateinit var radioBtnStudent: RadioButton
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var regBackLoginBtn: TextView
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    // here is the first branch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        databaseHelper = DatabaseHelper(this)

        radioBtnTeacher = findViewById(R.id.radio_as_teacher)
        radioBtnStudent = findViewById(R.id.radio_as_stu)
        regEmail = findViewById(R.id.email_register)
        registerBtn = findViewById(R.id.register_button)
        regPass = findViewById(R.id.password_register)
        regConfirmPass = findViewById(R.id.password_confirm_register)
        regBackLoginBtn = findViewById(R.id.click_back_to_login)
        progressBar = findViewById(R.id.register_progress)


        regBackLoginBtn.setOnClickListener {
            val intent = Intent(
                this@RegisterActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            regEmailStr = regEmail.text.toString()
            regPasswordStr = regPass.text.toString()
            confirmPassStr = regConfirmPass.text.toString()

            if (regEmailStr.isEmpty() || regPasswordStr.isEmpty() || confirmPassStr.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            role = when {
                radioBtnStudent.isChecked -> "student"
                radioBtnTeacher.isChecked -> "teacher"
                else -> "user"
            }

            if (regPasswordStr == confirmPassStr && role.isNotEmpty()) {
                signupDatabase(regEmailStr, regPasswordStr, role)
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password does not match or role not selected!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun signupDatabase(username: String, password: String, role: String) {
        try {
            // Handle role if null, e.g., defaulting to "user"
//            val userRole = role ?: "user"

            val insertRowId: Long = databaseHelper.insertUser(username, password, role)
            Log.d("RegisterActivity", "Insert Row ID: $insertRowId")

            if (insertRowId != -1L) {
                Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Signup failed!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Insertion Error: ${e.message}", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onBackPressed() {

        super.onBackPressed()
        val intent = Intent(
            this@RegisterActivity,
            LoginActivity::class.java
        )
        startActivity(intent)
    }

}