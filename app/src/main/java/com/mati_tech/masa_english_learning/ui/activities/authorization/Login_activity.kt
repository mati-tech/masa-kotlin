package com.example.masa_english_school.ui.activities.authorization

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
import com.mati_tech.masa_english_learning.R
import com.example.masa_english_school.authenticator.SessionManager
import com.example.masa_english_school.databaseHelper.DatabaseHelper
import com.example.masa_english_school.ui.activities.dashboards.Studentdashboard
import com.example.masa_english_school.ui.activities.dashboards.Teacherdashboard

class Login_activity : AppCompatActivity() {
    var email_login: EditText? = null
    var login_email_str: String? = null
    var databaseHelper: DatabaseHelper? = null
    var password_login: EditText? = null
    var login_pass_str: String? = null
    var progressBar: ProgressBar? = null
    var img_view: ImageView? = null
    var loginbtn_login_activity: Button? = null
    var click_to_register_text: TextView? = null

    var role: String? = null
    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.enableEdgeToEdge()
        databaseHelper = DatabaseHelper(this)
        //        databaseHelper.deleteDatabase();
        img_view = findViewById<ImageView>(R.id.masa_login_icon)
        email_login = findViewById<EditText>(R.id.username_email_login_activity)
        password_login = findViewById<EditText>(R.id.password_login_activity)
        progressBar = findViewById<ProgressBar>(R.id.login_progress)
        loginbtn_login_activity = findViewById<Button>(R.id.login_button_login_activity)
        click_to_register_text = findViewById<TextView>(R.id.click_to_register_view)


        loginbtn_login_activity.setOnClickListener(View.OnClickListener {
            progressBar.setVisibility(View.VISIBLE)
            login_email_str = email_login.getText().toString()
            login_pass_str = password_login.getText().toString()
            loginDatabase(login_email_str!!, login_pass_str!!)
            progressBar.setVisibility(View.INVISIBLE)
        })

        click_to_register_text.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@login_activity,
                register_activity::class.java
            )
            startActivity(intent)
            finish()
        })
        //admin login
        img_view.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@login_activity,
                Studentdashboard::class.java
            )
            startActivity(intent)
            finish()
        })
    }

    private fun loginDatabase(username: String, password: String) {
        val userExists: Boolean = databaseHelper.readUser(username, password)

        role = databaseHelper.getUserRole(username)


        if (userExists) {
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()


            val sessionManager = SessionManager(this)
            sessionManager.saveSession(username, role)

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
//                go to stu dashboard
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