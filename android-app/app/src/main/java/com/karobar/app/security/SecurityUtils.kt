package com.karobar.app.security

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Debug
import android.view.WindowManager
import java.io.File

/**
 * Enterprise-grade security & anti-reverse engineering checks for Karobar.
 * Protects against root, emulators, debuggers, tampering, and screen capture.
 */
object SecurityUtils {

    /**
     * Prevents screen recording, screenshots, and recent task snapshots.
     */
    fun enableScreenshotProtection(activity: Activity) {
        try {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } catch (_: Exception) {
            // Ignored on legacy unsupported devices
        }
    }

    /**
     * Checks if active debugger is attached to the process.
     */
    fun isDebuggerAttached(context: Context): Boolean {
        val isDebugConnected = Debug.isDebuggerConnected() || Debug.waitingForDebugger()
        val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        return isDebugConnected && !isDebuggable
    }

    /**
     * Multi-layered detection for rooted / compromised Android devices.
     */
    fun isDeviceRooted(): Boolean {
        return checkBuildTags() || checkSuFiles() || checkSuCommand()
    }

    private fun checkBuildTags(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkSuFiles(): Boolean {
        val commonPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in commonPaths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkSuCommand(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val exitValue = process.waitFor()
            exitValue == 0
        } catch (_: Exception) {
            false
        } finally {
            process?.destroy()
        }
    }

    /**
     * Checks if running inside an emulator (sandbox protection).
     */
    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk" == Build.PRODUCT)
    }

    data class SecurityStatus(
        val isRooted: Boolean,
        val isDebuggerAttached: Boolean,
        val isEmulator: Boolean,
        val isSecure: Boolean
    )

    fun checkDeviceSecurity(context: Context): SecurityStatus {
        val rooted = isDeviceRooted()
        val debugger = isDebuggerAttached(context)
        val emulator = isEmulator()
        val secure = !rooted && !debugger
        return SecurityStatus(
            isRooted = rooted,
            isDebuggerAttached = debugger,
            isEmulator = emulator,
            isSecure = secure
        )
    }
}
