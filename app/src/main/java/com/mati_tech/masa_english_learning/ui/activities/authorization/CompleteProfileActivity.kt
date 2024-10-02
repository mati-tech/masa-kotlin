package com.mati_tech.masa_english_learning.ui.activities.authorization

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.authenticator.SessionManager
import com.mati_tech.masa_english_learning.viewmodel.UserProfileViewModel
import com.mati_tech.masa_english_learning.models.Student
import com.mati_tech.masa_english_learning.models.Teacher

class CompleteProfileActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var englishLevelInput: EditText
    private lateinit var subjectInput: EditText
    private lateinit var nameStr: String
    private lateinit var lastnameStr: String
    private lateinit var englishlevelStr: String
    private lateinit var subjectStr: String
    private var age: Int = 0

    private lateinit var saveProfileButton: Button
    private lateinit var userProfileViewModel: UserProfileViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var role: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        nameInput = findViewById(R.id.name_input)
        lastNameInput = findViewById(R.id.last_name_input)
        ageInput = findViewById(R.id.age_input)
        englishLevelInput = findViewById(R.id.english_level_input)
        subjectInput = findViewById(R.id.subject_input)
        saveProfileButton = findViewById(R.id.save_profile_button)


        sessionManager = SessionManager(this)
        role = sessionManager.role.toString()
        userProfileViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        //username = sessionManager.getUsername();
        if (role == "teacher") {
            englishLevelInput.visibility = View.GONE
        } else if (role == "student") {
            subjectInput.visibility = View.GONE
        }
        saveProfileButton.setOnClickListener {
            nameStr = nameInput.getText().toString().trim()
            lastnameStr = lastNameInput.getText().toString().trim()
            age = ageInput.getText().toString().toInt()
            subjectStr = subjectInput.getText().toString()
            englishlevelStr = englishLevelInput.getText().toString()
            email = sessionManager.username.toString()

            if (role == "teacher") {
                val teacher = Teacher()
                teacher.name = nameStr
                teacher.lastName = lastnameStr
                teacher.age = age
                teacher.email = email
                teacher.subject = subjectStr
                userProfileViewModel.insertTeacher(teacher)
            } else if (role == "student") {
                val student = Student()
                student.name = nameStr
                student.lastName = lastnameStr
                student.age = age
                student.email = email
                student.englishLevel = englishlevelStr
                userProfileViewModel.insertStudent(student)
            }
            Toast.makeText(
                this@CompleteProfileActivity,
                "Profile saved successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            //saveProfile(name_str, lastname_str, age_str, username, role, subject_str, englishlevel_str);
        }
    } //    private void saveProfile(String name, String lastname, int age, String email, String role, String subject_str, String englishlevel) {
    //
    //        if (role.equals("teacher")) {
    //            Teacher teacher = new Teacher();
    //            teacher.email = email;
    //            teacher.name = name;
    //            teacher.age = age;
    //            teacher.lastName = lastname;
    //            teacher.subject = subject_str;
    //            AppDatabase.getInstance(this).teacherDao().insert(teacher);
    //        } else if (role.equals("student")) {
    //            Student student = new Student();
    //            student.email = email;
    //            student.name = name;
    //            student.age = age;
    //            student.lastName = lastname;
    //            student.englishLevel = englishlevel;
    //            AppDatabase.getInstance(this).studentDao().insert(student);
    //        }
    //
    //        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
    //
    //        // Set the result with the updated profile data
    //        Intent resultIntent = new Intent();
    //        resultIntent.putExtra("name", name);
    //        resultIntent.putExtra("lastName", lastname);
    //        resultIntent.putExtra("age", age);
    //        resultIntent.putExtra("subject", subject_str);
    //        resultIntent.putExtra("englishLevel", englishlevel);
    //        setResult(Activity.RESULT_OK, resultIntent);
    //
    //        finish();
    //    }
}