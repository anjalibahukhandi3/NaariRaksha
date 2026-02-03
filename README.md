# 🛡️ NaariSafe: Advanced Protection System (NaariRaksha)

# NaariRaksha

NaariRaksha is a full-stack safety application with an Android Kotlin frontend
and a Spring Boot backend, focused on women safety and awareness.


---

## ✨ Key Features

- **🚨 Instant SOS**: One-tap emergency alert that sends your live Google Maps location to all saved contacts.
- **🛡️ Stealth Safety Timer**: A "Safe Walk" feature that automatically triggers an SOS to your guardians if you don't check in within a set time.
- **🔊 Emergency Siren**: A high-decibel alarm to attract immediate attention in dangerous situations.
- **📞 Realistic Fake Call**: Simulate an incoming call with vibration and a professional interface to deter harassers.
- **📋 Helpline Directory**: Fast access to Women Helplines, Police, and Cyber Crime cells.
- **📳 Stealth Triggers**: Support for **Shake Detection** and **Power Button** SOS triggers even when the screen is off.
- **🔄 Full-Stack Integration**: Android client connected to a Spring Boot API for secure authentication and alert logging.

---

## 🛠️ Tech Stack

### **Mobile (Android)**
- **Language**: Kotlin
- **Architecture**: MVVM / ViewBinding
- **Networking**: Retrofit & Gson
- **Database**: Room SQL
- **Services**: Google Fused Location, Accelerometer (Shake), Broadcast Receivers (Power Button).

### **Backend (Server)**
- **Language**: Java / Spring Boot
- **Database**: MySQL
- **Tooling**: Maven, Postman for API testing.


## 🚀 How to Run

### **1. Backend Setup**
- Navigate to the `backend` folder.
- Run `BackendApplication.java` in your IDE.
- The server will start on `http://localhost:8080`.
- Access the H2 Database console at `http://localhost:8080/h2-console`.

### **2. Android Setup**
- Import the `app` folder into Android Studio or IntelliJ.
- Sync Gradle to download dependencies.
- Run on an Emulator or Physical device.
- *Note: If using an emulator, it connects to the backend via `10.0.2.2:8080`.*

---

## 🧪 API Testing (Postman)
You can test the following endpoints:
- `POST /api/users/register`: Register a new account.
- `POST /api/users/login`: Authenticate and sync profile.
- `POST /api/users/sos`: Log emergency alerts to the server.

---
*Built for safety, empowerment, and elegance.*
