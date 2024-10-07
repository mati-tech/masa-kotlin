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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.mati_tech.masa_english_learning.authenticator.SessionManager
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.activities.dashboards.Studentdashboard
import com.mati_tech.masa_english_learning.ui.activities.dashboards.Teacherdashboard
import com.mati_tech.masa_english_learning.databaseHelper.DatabaseHelper
import com.mati_tech.masa_english_learning.ui.activities.MainActivity
import com.mati_tech.masa_english_learning.viewmodel.AuthenticationViewModel

//class LoginActivity : AppCompatActivity() {
//    private lateinit var emailLogin: EditText
//    private lateinit var loginEmailStr: String
//    private lateinit var databaseHelper: DatabaseHelper
//    private lateinit var passwordLogin: EditText
//    private lateinit var loginPassStr: String
//    private lateinit var progressBar: ProgressBar
//    private lateinit var imgView: ImageView
//    private lateinit var loginLoginActivity: Button
//    private lateinit var clickToRegisterText: TextView
//    private lateinit var auth: FirebaseAuth
//    private lateinit var authViewModel: ViewModel
//    private var role: String? = null
//
////    override fun onStart() {
////        super.onStart()
////    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        this.enableEdgeToEdge()
////        databaseHelper = DatabaseHelper(this)
////        databaseHelper.deleteDatabase();
//        auth = FirebaseAuth.getInstance()
//        imgView = findViewById(R.id.masa_login_icon)
//        emailLogin = findViewById(R.id.username_email_login_activity)
//        passwordLogin = findViewById(R.id.password_login_activity)
//        progressBar = findViewById(R.id.login_progress)
//        loginLoginActivity = findViewById(R.id.login_button_login_activity)
//        clickToRegisterText = findViewById(R.id.click_to_register_view)
//
//        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
//        //Here the login button to login to the app
//        loginLoginActivity.setOnClickListener(View.OnClickListener {
//            progressBar.visibility = View.VISIBLE
//            loginEmailStr = emailLogin.getText().toString()
//            loginPassStr = passwordLogin.getText().toString()
////            loginDatabase(loginEmailStr, loginPassStr)
//
//            // Call signIn function from AuthenticationViewModel
//            (authViewModel as AuthenticationViewModel).login(loginEmailStr, loginPassStr) { signInSuccess ->
//                if (signInSuccess) {
//                    // Sign-in successful, navigate to MainActivity
//                    val intent = Intent(this@LoginActivity, Teacherdashboard::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    // Handle sign-in failure
//                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            progressBar.visibility = View.INVISIBLE
//        })
//
//        //Here to go for the registration page
//        clickToRegisterText.setOnClickListener(View.OnClickListener {
//            val intent = Intent(
//                this@LoginActivity,
//                RegisterActivity::class.java
//            )
//            startActivity(intent)
//            finish()
//        })
//
//        //admin login, but no access to the database
//        imgView.setOnClickListener(View.OnClickListener {
//            val intent = Intent(
//                this@LoginActivity,
//                Studentdashboard::class.java
//            )
//            startActivity(intent)
//            finish()
//        })
//    }
//
//    private fun loginDatabase(username: String, password: String) {
//        val userExists: Boolean = databaseHelper.readUser(username, password) // checking the data from database
//
//        role = databaseHelper.getUserRole(username) // role from database, stu or teacher
//
//        // if we have the user in the database->
//        if (userExists) {
//            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
//            val sessionManager = SessionManager(this) // for the Session inside the app
//            sessionManager.saveSession(username, role) // to save the email and the role of user
//
//            if (role == "teacher") {
//                //go to teacher dashboard
//                val intent = Intent(
//                    this,
//                    Teacherdashboard::class.java
//                )
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                Toast.makeText(this, "Teacher role", Toast.LENGTH_SHORT).show()
//                startActivity(intent)
//            } else if (role == "student") {
//                //go to stu dashboard
//                val intent = Intent(
//                    this,
//                    Studentdashboard::class.java
//                )
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                Toast.makeText(this, "Student role", Toast.LENGTH_SHORT).show()
//                startActivity(intent)
//            }
//        } else {
//            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//}



class LoginActivity : AppCompatActivity() {
    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var loginEmailStr: String
    private lateinit var loginPassStr: String
    private lateinit var progressBar: ProgressBar
    private lateinit var loginLoginActivity: Button
    private lateinit var clickToRegisterText: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance() // Initialize Firebase Auth
        emailLogin = findViewById(R.id.username_email_login_activity)
        passwordLogin = findViewById(R.id.password_login_activity)
        progressBar = findViewById(R.id.login_progress)
        loginLoginActivity = findViewById(R.id.login_button_login_activity)
        clickToRegisterText = findViewById(R.id.click_to_register_view)
        sessionManager = SessionManager(this)

        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        loginLoginActivity.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loginEmailStr = emailLogin.text.toString().trim()
            loginPassStr = passwordLogin.text.toString().trim()

            if (loginEmailStr.isEmpty() || loginPassStr.isEmpty()) {
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            // Firebase authentication
            authViewModel.login(loginEmailStr, loginPassStr) { signInSuccess ->
                if (signInSuccess) {
                    // Fetch the user's UID
                    val userId = auth.currentUser?.uid // Get the user ID (UID)

                    if (userId != null) {
                        // Use the UID to fetch the role
                        getUserRole(userId) { role ->
                            sessionManager.saveSession(loginEmailStr, role) // Save the session

                            when (role) {
                                "teacher" -> {
                                    val intent = Intent(this, Teacherdashboard::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }
                                "student" -> {
                                    val intent = Intent(this, Studentdashboard::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }
                                else -> {
                                    Toast.makeText(this, "Unknown role", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.INVISIBLE
            }
        }


        clickToRegisterText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUserRole(userId: String, callback: (String) -> Unit) {
        val db = FirebaseDatabase.getInstance().reference // Get a reference to the Realtime Database
        val userRoleRef = db.child("users").child(userId).child("role") // Use userId to access the user's role

        userRoleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the user exists and has a role
                val role = if (dataSnapshot.exists()) {
                    dataSnapshot.getValue(String::class.java) ?: "user" // Get the role, default to "user"
                } else {
                    "user" // Default role if the user does not exist
                }
                callback(role)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback("user") // Default role if there's an error
            }
        })
    }



}

