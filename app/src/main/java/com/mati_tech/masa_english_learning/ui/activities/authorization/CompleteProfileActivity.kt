package com.example.masa_english_school.ui.activities.authorization

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mati_tech.masa_english_learning.R
import com.example.masa_english_school.authenticator.SessionManager
import com.example.masa_english_school.models.Student
import com.example.masa_english_school.models.Teacher
import com.example.masa_english_school.viewmodel.UserProfileViewModel

class CompleteProfileActivity : AppCompatActivity() {
    var nameInput: EditText? = null
    var lastNameInput: EditText? = null
    var ageInput: EditText? = null
    var englishLevelInput: EditText? = null
    var subjectInput: EditText? = null
    var name_str: String? = null
    var lastname_str: String? = null
    var englishlevel_str: String? = null
    var subject_str: String? = null
    var age: Int = 0

    var saveProfileButton: Button? = null
    private var userProfileViewModel: UserProfileViewModel? = null
    var sessionManager: SessionManager? = null
    var role: String? = null
    var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        nameInput = findViewById<EditText>(R.id.name_input)
        lastNameInput = findViewById<EditText>(R.id.last_name_input)
        ageInput = findViewById<EditText>(R.id.age_input)
        englishLevelInput = findViewById<EditText>(R.id.english_level_input)
        subjectInput = findViewById<EditText>(R.id.subject_input)
        saveProfileButton = findViewById<Button>(R.id.save_profile_button)


        sessionManager = SessionManager(this)
        role = sessionManager!!.role
        userProfileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        //        username = sessionManager.getUsername();
        if (role == "teacher") {
            englishLevelInput.setVisibility(View.GONE)
        } else if (role == "student") {
            subjectInput.setVisibility(View.GONE)
        }

        saveProfileButton.setOnClickListener(View.OnClickListener {
            name_str = nameInput.getText().toString().trim { it <= ' ' }
            lastname_str = lastNameInput.getText().toString().trim { it <= ' ' }
            age = ageInput.getText().toString().toInt()
            subject_str = subjectInput.getText().toString()
            englishlevel_str = englishLevelInput.getText().toString()
            email = sessionManager!!.username

            if (role == "teacher") {
                val teacher: Teacher = Teacher()
                teacher.name = name_str
                teacher.lastName = lastname_str
                teacher.age = age
                teacher.email = email
                teacher.subject = subject_str
                userProfileViewModel.insertTeacher(teacher)
            } else if (role == "student") {
                val student: Student = Student()
                student.name = name_str
                student.lastName = lastname_str
                student.age = age
                student.email = email
                student.englishLevel = englishlevel_str
                userProfileViewModel.insertStudent(student)
            }
            Toast.makeText(
                this@CompleteProfileActivity,
                "Profile saved successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            //                saveProfile(name_str, lastname_str, age_str, username, role, subject_str, englishlevel_str);
        })
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