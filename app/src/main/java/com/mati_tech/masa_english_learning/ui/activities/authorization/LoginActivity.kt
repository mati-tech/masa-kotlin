package com.mati_tech.masa_english_learning.ui.activities.authorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.authenticator.SessionManager
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.activities.dashboards.Studentdashboard
import com.mati_tech.masa_english_learning.ui.activities.dashboards.Teacherdashboard
import com.mati_tech.masa_english_learning.databaseHelper.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var emailLogin: EditText
    private lateinit var loginEmailStr: String
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var passwordLogin: EditText
    private lateinit var loginPassStr: String
    private lateinit var progressBar: ProgressBar
    private lateinit var imgView: ImageView
    private lateinit var loginLoginActivity: Button
    private lateinit var clickToRegisterText: TextView

    private var role: String? = null

//    override fun onStart() {
//        super.onStart()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.enableEdgeToEdge()
        databaseHelper = DatabaseHelper(this)
        databaseHelper.deleteDatabase();
        imgView = findViewById(R.id.masa_login_icon)
        emailLogin = findViewById(R.id.username_email_login_activity)
        passwordLogin = findViewById(R.id.password_login_activity)
        progressBar = findViewById(R.id.login_progress)
        loginLoginActivity = findViewById(R.id.login_button_login_activity)
        clickToRegisterText = findViewById(R.id.click_to_register_view)

        //Here the login button to login to the app
        loginLoginActivity.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            loginEmailStr = emailLogin.getText().toString()
            loginPassStr = passwordLogin.getText().toString()
            loginDatabase(loginEmailStr, loginPassStr)
            progressBar.visibility = View.INVISIBLE
        })

        //Here to go for the registration page
        clickToRegisterText.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                RegisterActivity::class.java
            )
            startActivity(intent)
            finish()
        })

        //admin login, but no access to the database
        imgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                Studentdashboard::class.java
            )
            startActivity(intent)
            finish()
        })
    }

    private fun loginDatabase(username: String, password: String) {
        val userExists: Boolean = databaseHelper.readUser(username, password) // checking the data from database

        role = databaseHelper.getUserRole(username) // role from database, stu or teacher

        // if we have the user in the database->
        if (userExists) {
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            val sessionManager = SessionManager(this) // for the Session inside the app
            sessionManager.saveSession(username, role) // to save the email and the role of user

            if (role == "teacher") {
                //go to teacher dashboard
                val intent = Intent(
                    this,
                    Teacherdashboard::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                Toast.makeText(this, "Teacher role", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else if (role == "student") {
                //go to stu dashboard
                val intent = Intent(
                    this,
                    Studentdashboard::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                Toast.makeText(this, "Student role", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}