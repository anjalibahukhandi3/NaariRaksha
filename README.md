# 🛡️ NaariSafe: Advanced Protection System

NaariSafe is a professional, full-stack women's safety application designed with a high-end **Midnight & Gold** aesthetic. It combines a powerful Android client with a robust Spring Boot backend to provide real-time protection and emergency response.

## ✨ Key Features
- **🚨 Instant SOS**: One-tap emergency alert that sends your live Google Maps location to all saved contacts.
- **🛡️ Stealth Safety Timer**: A "Safe Walk" feature that automatically triggers an SOS if you don't check in within a set time.
- **🔊 Emergency Siren**: A high-decibel alarm to attract immediate attention in dangerous situations.
- **📞 Realistic Fake Call**: Simulate an incoming call with vibration and a professional interface to deter harassers.
- **📋 Helpline Directory**: Fast access to Women Helplines, Police, and Cyber Crime cells.
- **🔄 Full-Stack Integration**: Android client connected to a Spring Boot API for secure authentication and alert logging.

## 🛠️ Tech Stack
- **Android**: Kotlin, Material Design 3, Retrofit (API), Room (Local SQL), Google Play Services (Location).
- **Backend**: Kotlin, Spring Boot, Data JPA, H2/MySQL Database.
- **Aesthetics**: High-end Midnight Blue & Champagne Gold theme with Glassmorphism.

## 🚀 How to Run

### 1. Backend Setup
- Import the `backend` folder into IntelliJ IDEA.
- Run `BackendApplication.kt`.
- The server will start on `http://localhost:8080`.
- Access the H2 Database console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:naariraksha_db`).

### 2. Android Setup
- Import the `app` folder into Android Studio or IntelliJ.
- Sync Gradle to download dependencies (Retrofit, Room, etc.).
- Run on an Emulator or Physical device.
- *Note: If using an emulator, it connects to the backend via `10.0.2.2:8080`.*

## 🧪 Postman Testing
You can test the following endpoints:
- `POST /api/users/register`: Register a new user.
- `POST /api/users/login`: Authenticate a user.
- `POST /api/users/sos`: Log emergency alerts.

---
*Built with ❤️ for safety and empowerment.*
