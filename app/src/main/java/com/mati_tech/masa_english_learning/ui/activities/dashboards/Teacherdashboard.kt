package com.mati_tech.masa_english_learning.ui.activities.dashboards

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mati_tech.masa_english_learning.R
//import com.mati_tech.masa_english_learning.ui.fragments.fragments_dashboard.TeacherDashboadMainFragment
//import com.example.masa_english_school.ui.fragments.fragments_drawer.aboutus_fragment
//import com.example.masa_english_school.ui.fragments.fragments_drawer.calendar_fragment
//import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.courseMaterialTeacher_fragment
//import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.profile_fragment
//import com.example.masa_english_school.ui.fragments.fragments_navigations.extraStudy_fragment
//import com.example.masa_english_school.ui.fragments.fragments_navigations.grammer_fragment
//import com.example.masa_english_school.ui.fragments.fragments_navigations.vocabulary_fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mati_tech.masa_english_learning.ui.fragments.fragments_dashboard.TeacherDashboadMainFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.AboutusFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.CalendarFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.CourseMaterialTeacherFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.ProfileFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.ExtraStudyFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.grammer_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.VocabularyFragment
import java.util.Objects

class Teacherdashboard : AppCompatActivity(),
    TeacherDashboadMainFragment.OnFragmentInteractionListenerteacher {
    private lateinit var Teacher_page_drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggleTopLeftTeacherPage: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_teacherdashboard)

        fragmentManager = supportFragmentManager

        Teacher_page_drawer = findViewById(R.id.Teacher_dashboard_main_drawer_layout)
        navigationView = findViewById(R.id.drawer_menu_left_teacher_page_navigation_view)
        toggleTopLeftTeacherPage = ActionBarDrawerToggle(this@Teacherdashboard, Teacher_page_drawer, 0, 0)

        Teacher_page_drawer.addDrawerListener(toggleTopLeftTeacherPage)
        toggleTopLeftTeacherPage.syncState()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        actionBar = supportActionBar!!
        actionBar.title = "Teacher dashboard"

        // Initialize the first fragment main teacher dashboard.
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_of_teacher_dashboard, TeacherDashboadMainFragment())
                .commit()
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_nav_view_teacher)
        bottomNavigationView?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(TeacherDashboadMainFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_vocabulary -> {
                    replaceFragment(VocabularyFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_grammer -> {
                    replaceFragment(grammer_fragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_more -> {
                    replaceFragment(ExtraStudyFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        navigationView.setNavigationItemSelectedListener { item -> // Handle navigation item clicks
            if (item.itemId == R.id.drawer_profile) {
                // Handle destinations item click
                // Replace the current fragment with the "Los Angeles" fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_teacher_dashboard, ProfileFragment())
                    .commit()
                actionBar.title = "Profile"
            } else if (item.itemId == R.id.drawer_materials) {
                // Handle bookings item click
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_of_teacher_dashboard,
                        CourseMaterialTeacherFragment()
                    )
                    .commit()
                actionBar.title = "Course Material"
            } else if (item.itemId == R.id.drawer_calender) {
                // Handle bookings item click

                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_teacher_dashboard, CalendarFragment())
                    .commit()
                actionBar.title = "Calendar"
            } else if (item.itemId == R.id.drawer_about_us) {
                // Handle profile item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_teacher_dashboard, AboutusFragment())
                    .commit()
                actionBar.title = "About us"
            }

            //                else if (item.getItemId() == R.id.drawer_Settings) {
            //                    // Handle profile item click
            //                    getSupportFragmentManager().beginTransaction()
            //                            .replace(R.id.main_fragment_of_teacher_dashboard, new settings_fragment())
            //                            .commit();
            //                    actionBar.setTitle("Settings");
            //                }
            Teacher_page_drawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to the ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (toggleTopLeftTeacherPage.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_of_teacher_dashboard, fragment)
            .commit()
    }


    override fun onNavigateToFragmentTeacherDash(fragment: Fragment?) {
        replaceFragment(fragment!!)
    }
}