---
description: How to deploy the NaariRaksha full-stack application
---

# 🚀 Deployment Guide for NaariRaksha

This guide will help you deploy the Spring Boot backend and build the Android APK.

## 1. Backend Deployment (Spring Boot)

The easiest way to deploy the backend is using **Render** or **Railway**.

### Option A: Render (Free)
1.  **Create a GitHub Repository**:
    *   Initialize a new repo on GitHub.
    *   Upload the contents of `app/backend (1)/backend` to that repository.
2.  **Connect to Render**:
    *   Go to [Render.com](https://render.com/) and create a new **Web Service**.
    *   Connect your GitHub repo.
    *   Render will detect the `Dockerfile` I created.
3.  **Environment Variables**:
    *   In Render settings, add the following environment variables if you want to use a persistent database:
        *   `DB_URL`: The JDBC URL of your database (e.g., `jdbc:postgresql://hostname:port/dbname`).
        *   `DB_USER`: Your database username.
        *   `DB_PASSWORD`: Your database password.
        *   `DB_DRIVER`: `org.postgresql.Driver` (for PostgreSQL) or `com.mysql.cj.jdbc.Driver` (for MySQL).
        *   `DB_DIALECT`: `org.hibernate.dialect.PostgreSQLDialect` or `org.hibernate.dialect.MySQLDialect`.
4.  **Wait for Deployment**: Once finished, Render will provide a URL like `https://naariraksha-backend.onrender.com`.

## 2. Update Android App

1.  Open `app/src/main/java/com/example/naariraksha/data/api/RetrofitClient.kt`.
2.  Change the `BASE_URL` to your new Render URL:
    ```kotlin
    private const val BASE_URL = "https://naariraksha-backend.onrender.com/"
    ```

## 3. Build the Android APK

1.  Open a terminal in the root directory (`e:\NaariRaksha`).
// turbo
2.  Run the following command to build the debug APK:
    ```cmd
    gradlew assembleDebug
    ```
3.  The APK will be generated at:
    `app/build/outputs/apk/debug/app-debug.apk`

---
*Note: For a production release, you should generate a **Signed APK** using Android Studio's 'Build > Generate Signed Bundle / APK' menu.*
