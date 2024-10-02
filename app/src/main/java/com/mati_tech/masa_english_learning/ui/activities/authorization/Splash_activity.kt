package com.mati_tech.masa_english_learning.ui.activities.authorization

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mati_tech.masa_english_learning.R

class Splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(
                this@Splash_activity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish() // Close the SplashActivity
        }, 1000) // Delay in milliseconds (3000ms = 3s)
    }
}