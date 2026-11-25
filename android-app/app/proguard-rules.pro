# =========================================================================
# Karobar Android Security & Anti-Reverse Engineering ProGuard Rules
# =========================================================================

# 1. Advanced Obfuscation
-repackageclasses ''
-allowaccessmodification
-overloadaggressively

# 2. Strip Debug Information and Source References
-renamesourcefileattribute SourceFile
-keepattributes !SourceFile,!LineNumberTable,!LocalVariableTable,!LocalVariableTypeTable

# 3. Strip All Debug Logging Statements from Release APK
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
}

# 4. Standard Android Lifecycle Components Keep Rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# 5. Keep View Constructors for Layout Inflation
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 6. ViewBinding Protection
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
    public static *** inflate(...);
    public static *** bind(...);
    public *** getRoot();
}

# 7. Kotlin Coroutines & Reflection Keep Rules
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

# 8. Material Design Components
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }
