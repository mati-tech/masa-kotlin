package com.mati_tech.masa_english_learning.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.aboutus_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.calendar_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.profile_fragment
import com.google.android.material.navigation.NavigationView
import java.util.Objects

class MainActivity : AppCompatActivity() {
    var main_page_drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toggle_top_left_main_page: ActionBarDrawerToggle? = null
    var actionBar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_page_drawer = findViewById<DrawerLayout>(R.id.main_page_drawer_id)
        navigationView =
            findViewById<NavigationView>(R.id.drawer_menu_left_main_page_navigation_view)
        toggle_top_left_main_page = ActionBarDrawerToggle(this@MainActivity, main_page_drawer, 0, 0)

        if (main_page_drawer != null) {
            main_page_drawer!!.addDrawerListener(toggle_top_left_main_page!!)
        }
        toggle_top_left_main_page!!.syncState()
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)


        actionBar = supportActionBar
        actionBar!!.setTitle("Home")

        navigationView?.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item -> // Handle navigation item clicks
            if (item.itemId == R.id.drawer_profile) {
                // Handle destinations item click
                // Replace the current fragment with the "Los Angeles" fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, profile_fragment())
                    .commit()
            } else if (item.itemId == R.id.drawer_calender) {
                // Handle bookings item click

                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, calendar_fragment())
                    .commit()
            } else if (item.itemId == R.id.drawer_about_us) {
                // Handle profile item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, aboutus_fragment())
                    .commit()
            }

            //                else if (item.getItemId() == R.id.drawer_Settings) {
            //                    // Handle profile item click
            //                    getSupportFragmentManager().beginTransaction()
            //                            .replace(R.id.main_fragment_of_main_activity, new settings_fragment())
            //                            .commit();
            //                }
            main_page_drawer?.closeDrawers()
            true
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to the ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (toggle_top_left_main_page!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}