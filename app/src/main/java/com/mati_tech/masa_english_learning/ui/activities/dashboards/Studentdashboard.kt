package com.example.masa_english_school.ui.activities.dashboards

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mati_tech.masa_english_learning.R
import com.example.masa_english_school.ui.fragments.fragments_dashboards.StudentDashboardMainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.aboutus_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.calendar_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.courseMaterialStudent_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.profile_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.extraStudy_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.grammer_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.vocabulary_fragment
import java.util.Objects

class Studentdashboard : AppCompatActivity(),
    StudentDashboardMainFragment.OnFragmentInteractionListener {
    lateinit var student_page_drawer: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toggle_top_left_student_page: ActionBarDrawerToggle
    lateinit var actionBar: ActionBar
    lateinit var fragmentManager: FragmentManager


    lateinit var goGrammer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentdashboard)


        // Add the fragment
        fragmentManager = supportFragmentManager
        val stumainfragment: StudentDashboardMainFragment = StudentDashboardMainFragment()


        student_page_drawer = findViewById<DrawerLayout>(R.id.Student_dashboard_main_drawer_layout)
        navigationView =
            findViewById<NavigationView>(R.id.drawer_menu_left_student_page_navigation_view)
        toggle_top_left_student_page =
            ActionBarDrawerToggle(this@Studentdashboard, student_page_drawer, 0, 0)

        student_page_drawer.addDrawerListener(toggle_top_left_student_page)
        toggle_top_left_student_page.syncState()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)


        actionBar = supportActionBar!!
        actionBar.setTitle("Student dashboard")

        // Initialize the first fragment
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_of_student_dashboard, StudentDashboardMainFragment())
                .commit()
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_nav_view_stu)
        bottomNavigationView?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(StudentDashboardMainFragment())
                    actionBar.setTitle("Student Dashboard")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_vocabulary -> {
                    replaceFragment(vocabulary_fragment())
                    actionBar.setTitle("English Vocabulary")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_grammer -> {
                    replaceFragment(grammer_fragment())
                    actionBar.setTitle("English Grammer")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_more -> {
                    replaceFragment(extraStudy_fragment())
                    actionBar.setTitle("Extras")
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }


        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item -> // Handle navigation item clicks
            if (item.itemId == R.id.drawer_profile) {
                // Handle destinations item click
                // Replace the current fragment with the "Los Angeles" fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_student_dashboard, profile_fragment())
                    .commit()
                actionBar.setTitle("Profile")
            } else if (item.itemId == R.id.drawer_materials) {
                // Handle bookings item click
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_of_student_dashboard,
                        courseMaterialStudent_fragment()
                    )
                    .commit()
                actionBar.setTitle("Course Material")
            } else if (item.itemId == R.id.drawer_calender) {
                // Handle bookings item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_student_dashboard, calendar_fragment())
                    .commit()
                actionBar.setTitle("Calendar")
            } else if (item.itemId == R.id.drawer_about_us) {
                // Handle profile item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_student_dashboard, aboutus_fragment())
                    .commit()
                actionBar.setTitle("About us")
            }

            //                else if (item.getItemId() == R.id.drawer_Settings) {
            //                    // Handle profile item click
            //                    getSupportFragmentManager().beginTransaction()
            //                            .replace(R.id.main_fragment_of_student_dashboard, new settings_fragment())
            //                            .commit();
            //
            //                }
            student_page_drawer.closeDrawers()
            true
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to the ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (toggle_top_left_student_page.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    //    private void setupBottomNavigationView() {
    //        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
    //        if (bottomNavigationView != null) {
    //            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
    //                switch (item.getItemId()) {
    //                    case R.id.nav_home:
    //                        replaceFragment(new HomeFragment());
    //                        return true;
    //                    case R.id.nav_explore:
    //                        replaceFragment(new ExploreFragment());
    //                        return true;
    //                    case R.id.nav_favorites:
    //                        replaceFragment(new FavoritesFragment());
    //                        return true;
    //                    default:
    //                        return false;
    //                }
    //            });
    //        }
    //    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_of_student_dashboard, fragment)
            .commit()
    }

    override fun onNavigateToFragment(fragment: Fragment?) {
        fragment?.let { replaceFragment(it) }
    }

}