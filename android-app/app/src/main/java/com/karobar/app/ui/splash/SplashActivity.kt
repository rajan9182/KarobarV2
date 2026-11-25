package com.karobar.app.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.karobar.app.R
import com.karobar.app.databinding.ActivitySplashBinding
import com.karobar.app.security.SecurityUtils
import com.karobar.app.ui.main.MainActivity

/**
 * Official, high-performance native Splash Screen.
 * Inspired by modern tech apps (Blinkit, Zepto, Swiggy).
 * Pure vector typography with Plus Jakarta Sans and smooth physics animations.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private var hasNavigated = false

    private val navigationRunnable = Runnable {
        navigateToHome()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SecurityUtils.enableScreenshotProtection(this)

        // Ultra-clean pure white status & navigation bar
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = Color.WHITE
        window.navigationBarColor = Color.WHITE

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startBlinkitStyleAnimations()

        // Snappy, professional 2.4s splash duration
        handler.postDelayed(navigationRunnable, 2400L)
    }

    private fun startBlinkitStyleAnimations() {
        // 1. Logo Entrance (Overshoot Spring Bounce)
        binding.logoContainer.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(650L)
            .setInterpolator(OvershootInterpolator(1.4f))
            .start()

        // 2. Brand Name Slide-up & Fade-in
        binding.brandTitleContainer.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(550L)
            .setStartDelay(250L)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // 3. Tagline Slide-up & Fade-in
        binding.tvTagline.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(550L)
            .setStartDelay(400L)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // 4. Footer Trust Badge Fade-in
        binding.footerContainer.animate()
            .alpha(1f)
            .setDuration(500L)
            .setStartDelay(650L)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    private fun navigateToHome() {
        if (hasNavigated) return
        hasNavigated = true

        handler.removeCallbacks(navigationRunnable)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Smooth fade-in & fade-out transition
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                R.anim.fade_in,
                R.anim.fade_out
            )
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacks(navigationRunnable)
        super.onDestroy()
    }
}
