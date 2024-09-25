package com.example.masa_english_school.ui.activities.authorization

import android.content.Intent
import android.os.Bundle
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
// TODO:  import com.mati_tech.masa_english_learning.databaseHelper.DatabaseHelper
import com.mati_tech.masa_english_learning.ui.activities.authorization.Login_activity

class Register_activity : AppCompatActivity() {
    lateinit var reg_email: EditText
    lateinit var reg_pass: EditText
    lateinit var reg_confirm_pass: EditText
    lateinit var reg_email_str: String
    lateinit var reg_password_str: String
    lateinit var confirm_pass_str: String
    lateinit var radio_btn_teacher: RadioButton
    lateinit var role: String
    lateinit var radio_btn_student: RadioButton
    lateinit var databaseHelper: DatabaseHelper
    lateinit var reg_back_login_btn: TextView
    lateinit var register_btn: Button
    lateinit var progressBar: ProgressBar

    // here is the first branch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
       databaseHelper = DatabaseHelper(this)

        radio_btn_teacher = findViewById<RadioButton>(R.id.radio_as_teacher)
        radio_btn_student = findViewById<RadioButton>(R.id.radio_as_stu)
        reg_email = findViewById<EditText>(R.id.email_register)
        register_btn = findViewById<Button>(R.id.register_button)
        reg_pass = findViewById<EditText>(R.id.password_register)
        reg_confirm_pass = findViewById<EditText>(R.id.password_confirm_register)
        reg_back_login_btn = findViewById<TextView>(R.id.click_back_to_login)
        progressBar = findViewById<ProgressBar>(R.id.register_progress)


        reg_back_login_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@Register_activity,
                Login_activity::class.java
            )
            startActivity(intent)
        })

        register_btn.setOnClickListener(View.OnClickListener {
            reg_email_str = reg_email.getText().toString()
            reg_password_str = reg_pass.getText().toString()
            confirm_pass_str = reg_confirm_pass.getText().toString()

            if (radio_btn_student.isChecked()) {
                role = "student"
            } else if (radio_btn_teacher.isChecked()) {
                role = "teacher"
            }
            if (reg_password_str == confirm_pass_str) {
//                signupDatabase(reg_email_str!!, reg_password_str!!, role)
            } else {
                Toast.makeText(
                    this@Register_activity,
                    "Password does not match!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun signupDatabase(username: String, password: String, role: String?) {

        val insertRowId: Long = databaseHelper.insertUser(username, password, role)

        if (insertRowId != -1L) {
            Toast.makeText(this, "Signup Successfull!", Toast.LENGTH_SHORT).show()
            val intent = Intent(
                this,
                Login_activity::class.java
            )
            startActivity(intent)
            finish()
        }

        else {
            Toast.makeText(this, "Signup failed!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {

        super.onBackPressed()
        val intent = Intent(
            this@Register_activity,
            Login_activity::class.java
        )
        startActivity(intent)
    }

}