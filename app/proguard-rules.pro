# Preserve Kotlin metadata
-keep class kotlin.** { *; }
-keepclassmembers class ** {
    @kotlin.Metadata *;
}

# Retrofit (keep model classes and interfaces)
-keep class com.example.recipe_app.model.** { *; }
-keep interface com.example.recipe_app.api.** { *; }

# Gson or Moshi (whichever you're using with Retrofit)
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Glide
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

# Keep Parcelable model (Kotlin Parcelize)
-keep class kotlinx.parcelize.** { *; }
-keep class com.example.recipe_app.data.model.Recipe { *; }

# For Fragments (to prevent issues with reflection)
-keep public class * extends androidx.fragment.app.Fragment

# If using ViewBinding or DataBinding
-keep class **.databinding.*Binding { *; }

# Avoid warnings
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn okio.**

# Keep application classes
-keep class com.example.recipe_app.** { *; }

# Optional: keep activity classes
-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
