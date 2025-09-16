# Keep everything inside Usercentrics SDK
-keep class com.usercentrics.** { *; }
-dontwarn com.usercentrics.**

# Keep annotations and signatures (reflection based frameworks need these)
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature

# Keep all interfaces and callbacks (sometimes they’re stripped separately)
-keep interface com.usercentrics.** { *; }

# Keep all public methods (protects API entrypoints)
-keepclassmembers class com.usercentrics.** {
    public *;
}

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# Gson (if used internally)
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**