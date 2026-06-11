# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# NaariRaksha models
-keep class com.naariraksha.** { *; }

# Keep DTOs
-keep class com.naariraksha.model.** { *; }

# Keep Retrofit API interfaces
-keep interface com.naariraksha.network.** { *; }

# Keep annotations
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations