package com.karobar.app.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.karobar.app.R
import com.karobar.app.databinding.ActivitySplashBinding
import com.karobar.app.security.SecurityUtils
import com.karobar.app.ui.main.MainActivity

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

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make full screen and hide system bars using modern WindowInsetsControllerCompat
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setupVideoSplash()
    }

    private fun setupVideoSplash() {
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.splash_screen}")
        binding.videoViewSplash.setVideoURI(videoUri)

        binding.videoViewSplash.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = false
            binding.videoViewSplash.start()
        }

        binding.videoViewSplash.setOnCompletionListener {
            navigateToHome()
        }

        binding.videoViewSplash.setOnErrorListener { _, _, _ ->
            navigateToHome()
            true
        }

        // Strictly enforce 4 seconds duration as required
        handler.postDelayed(navigationRunnable, 4000L)
    }

    private fun navigateToHome() {
        if (hasNavigated) return
        hasNavigated = true

        handler.removeCallbacks(navigationRunnable)
        try {
            if (binding.videoViewSplash.isPlaying) {
                binding.videoViewSplash.stopPlayback()
            }
        } catch (_: Exception) {
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Smooth fade effect transition to Home Screen
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
        try {
            binding.videoViewSplash.stopPlayback()
        } catch (_: Exception) {
        }
        super.onDestroy()
    }
}
