package com.mati_tech.masa_english_learning.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.AboutusFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.CalendarFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer.ProfileFragment
import com.google.android.material.navigation.NavigationView
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var mainPageDrawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggleTopLeftMainPage: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPageDrawer = findViewById(R.id.main_page_drawer_id)
        navigationView =
            findViewById(R.id.drawer_menu_left_main_page_navigation_view)
        toggleTopLeftMainPage = ActionBarDrawerToggle(this@MainActivity, mainPageDrawer, 0, 0)

        mainPageDrawer.addDrawerListener(toggleTopLeftMainPage)
        toggleTopLeftMainPage.syncState()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)


        actionBar = supportActionBar!!
        actionBar.setTitle("Home")

        navigationView.setNavigationItemSelectedListener { item -> // Handle navigation item clicks
            if (item.itemId == R.id.drawer_profile) {
                // Handle destinations item click
                // Replace the current fragment with the "Los Angeles" fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, ProfileFragment())
                    .commit()
            } else if (item.itemId == R.id.drawer_calender) {
                // Handle bookings item click

                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, CalendarFragment())
                    .commit()
            } else if (item.itemId == R.id.drawer_about_us) {
                // Handle profile item click
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_of_main_activity, AboutusFragment())
                    .commit()
            }

            //                else if (item.getItemId() == R.id.drawer_Settings) {
            //                    // Handle profile item click
            //                    getSupportFragmentManager().beginTransaction()
            //                            .replace(R.id.main_fragment_of_main_activity, new settings_fragment())
            //                            .commit();
            //                }
            mainPageDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to the ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event
        if (toggleTopLeftMainPage.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}