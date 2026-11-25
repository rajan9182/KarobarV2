package com.karobar.app.ui.main

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.karobar.app.databinding.ActivityMainBinding
import com.karobar.app.security.SecurityUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enforce anti-reverse engineering & screenshot protection
        SecurityUtils.enableScreenshotProtection(this)

        // Ultra-clean white status and navigation bars
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = Color.WHITE
        window.navigationBarColor = Color.WHITE

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Floating Mic Action
        binding.btnFloatingMic.setOnClickListener {
            Toast.makeText(this, "Voice Assistant: Speak to add sale or expense", Toast.LENGTH_SHORT).show()
        }

        // Top Bar Actions
        binding.btnSearch.setOnClickListener {
            Toast.makeText(this, "Search invoices, customers, and items", Toast.LENGTH_SHORT).show()
        }

        binding.btnNotification.setOnClickListener {
            Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show()
        }

        binding.btnStore.setOnClickListener {
            Toast.makeText(this, "Store Profile: R.K. Enterprises", Toast.LENGTH_SHORT).show()
        }
    }
}
