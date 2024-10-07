//package com.mati_tech.masa_english_learning.ui.activities.authorization
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ProgressBar
//import android.widget.RadioButton
//import android.widget.TextView
//import android.widget.Toast
//import com.google.firebase.FirebaseApp
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.mati_tech.masa_english_learning.R
//import com.mati_tech.masa_english_learning.databaseHelper.DatabaseHelper
//import com.mati_tech.masa_english_learning.viewmodel.AuthenticationViewModel
//
//class RegisterActivity : AppCompatActivity() {
//    private lateinit var regEmail: EditText
//    private lateinit var regPass: EditText
//    private lateinit var regConfirmPass: EditText
//    private lateinit var regEmailStr: String
//    private lateinit var regPasswordStr: String
//    private lateinit var confirmPassStr: String
//    private lateinit var radioBtnTeacher: RadioButton
//    private lateinit var role: String
//    private lateinit var radioBtnStudent: RadioButton
//    private lateinit var databaseHelper: DatabaseHelper
//    private lateinit var regBackLoginBtn: TextView
//    private lateinit var registerBtn: Button
//    private lateinit var progressBar: ProgressBar
//    private lateinit var auth: FirebaseAuth
//    private lateinit var authViewModel: ViewModel
//
//    // here is the first branch
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.register_activity)
//        FirebaseApp.initializeApp(this);
////        databaseHelper = DatabaseHelper(this)
//        auth = Firebase.auth
//
//        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
//        radioBtnTeacher = findViewById(R.id.radio_as_teacher)
//        radioBtnStudent = findViewById(R.id.radio_as_stu)
//        regEmail = findViewById(R.id.email_register)
//        registerBtn = findViewById(R.id.register_button)
//        regPass = findViewById(R.id.password_register)
//        regConfirmPass = findViewById(R.id.password_confirm_register)
//        regBackLoginBtn = findViewById(R.id.click_back_to_login)
//        progressBar = findViewById(R.id.register_progress)
//
//
//        regBackLoginBtn.setOnClickListener {
//            val intent = Intent(
//                this@RegisterActivity,
//                LoginActivity::class.java
//            )
//            startActivity(intent)
//        }
//
//        registerBtn.setOnClickListener {
//            regEmailStr = regEmail.text.toString()
//            regPasswordStr = regPass.text.toString()
//            confirmPassStr = regConfirmPass.text.toString()
//
//            if (regEmailStr.isEmpty() || regPasswordStr.isEmpty() || confirmPassStr.isEmpty()) {
//                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            role = when {
//                radioBtnStudent.isChecked -> "student"
//                radioBtnTeacher.isChecked -> "teacher"
//                else -> "user"
//            }
//
//            if (regPasswordStr == confirmPassStr ) {
////                signupDatabase(regEmailStr, regPasswordStr, role)
//                (authViewModel as AuthenticationViewModel).register(regEmailStr, regPasswordStr) { registrationSuccess ->
//                    if (registrationSuccess) {
//                        // Registration successful, navigate to Login activity
////                    val intent = Intent(this@RegisterActivity, Log
//                        //                    inActivity::class.java)
////                    startActivity(intent)
////                    finish()
//                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//
//                    } else {
//                        // Handle registration failure
//                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    "Password does not match!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//    }
//
////    private fun signupDatabase(username: String, password: String, role: String) {
////        try {
////            // Handle role if null, e.g., defaulting to "user"
//////            val userRole = role ?: "user"
////
////            val insertRowId: Long = databaseHelper.insertUser(username, password, role)
////            Log.d("RegisterActivity", "Insert Row ID: $insertRowId")
////
////            if (insertRowId != -1L) {
////                Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
////                val intent = Intent(this, LoginActivity::class.java)
////                startActivity(intent)
////                finish()
////            } else {
////                Toast.makeText(this, "Signup failed!", Toast.LENGTH_SHORT).show()
////            }
////        } catch (e: Exception) {
////            Log.e("RegisterActivity", "Insertion Error: ${e.message}", e)
////            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
////        }
////    }
//
//
//    override fun onBackPressed() {
//
//        super.onBackPressed()
//        val intent = Intent(
//            this@RegisterActivity,
//            LoginActivity::class.java
//        )
//        startActivity(intent)
//        finish()
//    }
//
//}


package com.mati_tech.masa_english_learning.ui.activities.authorization

import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.viewmodel.AuthenticationViewModel

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
    private lateinit var regBackLoginBtn: TextView
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        // Initialize views
        radioBtnTeacher = findViewById(R.id.radio_as_teacher)
        radioBtnStudent = findViewById(R.id.radio_as_stu)
        regEmail = findViewById(R.id.email_register)
        registerBtn = findViewById(R.id.register_button)
        regPass = findViewById(R.id.password_register)
        regConfirmPass = findViewById(R.id.password_confirm_register)
        regBackLoginBtn = findViewById(R.id.click_back_to_login)
        progressBar = findViewById(R.id.register_progress)

        // Back to login page
        regBackLoginBtn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // Register user
        registerBtn.setOnClickListener {
            regEmailStr = regEmail.text.toString().trim()
            regPasswordStr = regPass.text.toString().trim()
            confirmPassStr = regConfirmPass.text.toString().trim()

            if (regEmailStr.isEmpty() || regPasswordStr.isEmpty() || confirmPassStr.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            role = when {
                radioBtnStudent.isChecked -> "student"
                radioBtnTeacher.isChecked -> "teacher"
                else -> "user"
            }

            if (regPasswordStr == confirmPassStr) {
                registerUser(regEmailStr, regPasswordStr, role)
            } else {
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, role: String) {
        // Firebase Authentication to create the user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User registered successfully
                    val userId = auth.currentUser?.uid

                    if (userId != null) {
                        // Store user data in Realtime Database
                        saveUserDataToDatabase(userId, email, role)

                        // Redirect to login or another activity
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Handle registration failure
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserDataToDatabase(userId: String, email: String, role: String) {
        // Get reference to Firebase Realtime Database
        val database = Firebase.database.reference

        // Create a map with the user data
        val userData = mapOf(
            "email" to email,
            "role" to role
        )

        // Save user data under "users/{userId}" path
        database.child("users").child(userId).setValue(userData)
            .addOnSuccessListener {
                // Data successfully saved
                Toast.makeText(this, "User data saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Toast.makeText(this, "Failed to save user data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}




