package com.mati_tech.masa_english_learning.ui.activities.dashboards

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
import com.mati_tech.masa_english_learning.ui.fragments.fragments_dashboard.StudentDashboardMainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.AboutusFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.CalendarFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.CourseMaterialStudentFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.ProfileFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.ExtraStudyFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.GrammerFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.VocabularyFragment
import java.util.Objects

class Studentdashboard : AppCompatActivity(),
    StudentDashboardMainFragment.OnFragmentInteractionListener {
    private lateinit var studentPageDrawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggleTopLeftStudentPage: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar
    private lateinit var fragmentManager: FragmentManager


    lateinit var goGrammer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentdashboard)


        // Add the fragment
        fragmentManager = supportFragmentManager
        val stumainfragment: StudentDashboardMainFragment = StudentDashboardMainFragment()


        studentPageDrawer = findViewById(R.id.Student_dashboard_main_drawer_layout)
        navigationView = findViewById(R.id.drawer_menu_left_student_page_navigation_view)
        toggleTopLeftStudentPage = ActionBarDrawerToggle(this@Studentdashboard, studentPageDrawer, 0, 0)

        studentPageDrawer.addDrawerListener(toggleTopLeftStudentPage)
        toggleTopLeftStudentPage.syncState()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)


        actionBar = supportActionBar!!
        actionBar.title = "Student dashboard"

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
                    actionBar.title = "Student Dashboard"
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_vocabulary -> {
                    replaceFragment(VocabularyFragment())
                    actionBar.title = "English Vocabulary"
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_grammer -> {
                    replaceFragment(GrammerFragment())
                    actionBar.title = "English Grammer"
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_more -> {
                    replaceFragment(ExtraStudyFragment())
                    actionBar.title = "Extras"
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
                    .replace(R.id.main_fragment_of_student_dashboard, ProfileFragment())
                    .commit()
                actionBar.title = "Profile"
            } else if (item.itemId == R.id.drawer_materials) {
                // Handle bookings item click
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_of_student_dashboard,
                        CourseMaterialStudentFragment()
                    )
                    .commit()
                actionBar.title = "Course Material"
            } else if (item.itemId == R.id.drawer_calender) {
                // Handle bookings item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_student_dashboard, CalendarFragment())
                    .commit()
                actionBar.title = "Calendar"
            } else if (item.itemId == R.id.drawer_about_us) {
                // Handle profile item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_student_dashboard, AboutusFragment())
                    .commit()
                actionBar.title = "About us"
            }

            //                else if (item.getItemId() == R.id.drawer_Settings) {
            //                    // Handle profile item click
            //                    getSupportFragmentManager().beginTransaction()
            //                            .replace(R.id.main_fragment_of_student_dashboard, new settings_fragment())
            //                            .commit();
            //
            //                }
            studentPageDrawer.closeDrawers()
            true
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to the ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (toggleTopLeftStudentPage.onOptionsItemSelected(item)) {
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