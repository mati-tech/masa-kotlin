package com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.authenticator.SessionManager

import com.mati_tech.masa_english_learning.ui.activities.authorization.CompleteProfileActivity
import com.mati_tech.masa_english_learning.ui.activities.authorization.LoginActivity
import com.mati_tech.masa_english_learning.viewmodel.UserProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var CompleteProf: Button
    private lateinit var signout: ImageButton

    private lateinit var username: String
    private lateinit var role: String
    private lateinit var prof_info: TextView
    private lateinit var nameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var subjectTextView: TextView
    private lateinit var englishLevelTextView: TextView
    private lateinit var roleView: TextView
    private lateinit var EmailView: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var userProfileViewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = context?.let { SessionManager(it) }!!
        username = sessionManager.username.toString()
        role = sessionManager.role.toString()
        userProfileViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        // Initialize UI elements
        nameTextView = view.findViewById(R.id.name_view)
        lastNameTextView = view.findViewById(R.id.lastname_view)
        ageTextView = view.findViewById(R.id.age_view)
        subjectTextView = view.findViewById(R.id.subject_view)
        englishLevelTextView = view.findViewById(R.id.englishlevel_view)
        roleView = view.findViewById(R.id.role_view)
        EmailView = view.findViewById(R.id.email_view)

        roleView.setText("Role: $role")
        EmailView.setText("Email: $username")

        prof_info = view.findViewById(R.id.frag_profile_info_login)
        prof_info.text = role + "Profile Info"
        CompleteProf = view.findViewById(R.id.frag_profile_complete_button)
        signout = view.findViewById(R.id.frag_profile_sign_out_button)

        //here use the role and the email from here
//        prof_info.setText("Username: "+ username + "\n"+ "Role as: "+ role );
        signout.setOnClickListener {
            val intent = Intent(
                context,
                LoginActivity::class.java
            )
            startActivity(intent)
            requireActivity().finish()
        }
        CompleteProf.setOnClickListener {
            val intent = Intent(
                context,
                CompleteProfileActivity::class.java
            )
            startActivity(intent)
        }
        loadUserProfile()
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserProfile() {
        if (role == "teacher") {
            userProfileViewModel.getTeacherByEmail(username)?.observe(viewLifecycleOwner
            ) { teacher ->
                if (teacher != null) {
                    nameTextView.text = "Name: " + teacher.name
                    lastNameTextView.text = "Lastname: " + teacher.lastName
                    ageTextView.text = java.lang.String.valueOf("Age: " + teacher.age)
                    subjectTextView.text = "Subject: " + teacher.subject
                    englishLevelTextView.visibility = View.GONE
                }
            }
        } else if (role == "student") {
            userProfileViewModel.getStudentByEmail(username)?.observe(viewLifecycleOwner
            ) { student ->
                if (student != null) {
                    nameTextView.text = "Name: " + student.name
                    lastNameTextView.text = "Lastname: " + student.lastName
                    ageTextView.text = java.lang.String.valueOf("Age: " + student.age)
                    englishLevelTextView.text = "English Level: " + student.englishLevel
                    subjectTextView.visibility = View.GONE
                }
            }
        }
    } //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if (requestCode == REQUEST_COMPLETE_PROFILE && resultCode == AppCompatActivity.RESULT_OK) {
    //            // Get the updated profile data from the result
    //            assert data != null;
    //            String name = data.getStringExtra("name");
    //            String lastName = data.getStringExtra("lastName");
    //            int age = data.getIntExtra("age", 0);
    //            String subject = data.getStringExtra("subject");
    //            String englishLevel = data.getStringExtra("englishLevel");
    //
    //            // Update the UI with the updated profile data
    //            nameTextView.setText(name);
    //            lastNameTextView.setText(lastName);
    //            ageTextView.setText(String.valueOf(age));
    //            subjectTextView.setText(subject);
    //            englishLevelTextView.setText(englishLevel);
    //        }
    //    }


    companion object {
        private const val REQUEST_COMPLETE_PROFILE = 1
    }
}