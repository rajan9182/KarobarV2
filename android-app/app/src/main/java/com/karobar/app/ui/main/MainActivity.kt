package com.karobar.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karobar.app.databinding.ActivityMainBinding
import com.karobar.app.security.SecurityUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enforce anti-reverse engineering & screenshot protection
        SecurityUtils.enableScreenshotProtection(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Minimalist Hello World Home Screen
        val securityStatus = SecurityUtils.checkDeviceSecurity(this)
        if (securityStatus.isSecure) {
            binding.cardWelcome.strokeColor = getColor(com.karobar.app.R.color.border_color)
        }
    }
}
