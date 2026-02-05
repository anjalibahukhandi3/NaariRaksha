# 🛡️ NaariRaksha: Women's Safety Full-Stack Application

A comprehensive safety platform with Android app and Spring Boot backend, focused on women's safety and emergency response.

## 📱 Project Structure

```
NaariRaksha/
├── app/                          # Android Application (Kotlin)
│   ├── src/                      # Android source code
│   └── backend (1)/backend/      # Spring Boot Backend
└── README.md
```

---

## ✨ Key Features

### 🚨 Safety Features
- **Instant SOS Alert**: One-tap emergency alert with live Google Maps location sharing
- **Stealth Safety Timer**: Auto-trigger SOS if you don't check in within set time
- **Emergency Siren**: High-decibel alarm for immediate attention
- **Fake Call Simulation**: Realistic incoming call to deter harassers
- **Shake Detection**: Trigger SOS by shaking your device
- **Power Button SOS**: Emergency trigger even when screen is off

### 📞 Support & Integration
- **Helpline Directory**: Quick access to Women Helplines, Police, Cyber Crime
- **Contact Management**: Save emergency contacts with Room database
- **Real-time Location**: Live GPS tracking with Google Fused Location
- **Backend Integration**: Secure authentication and alert logging

---

## 🛠️ Tech Stack

### **Android App**
- **Language**: Kotlin
- **Architecture**: MVVM with ViewBinding
- **Networking**: Retrofit 2 + Gson
- **Local Database**: Room SQL
- **Location**: Google Fused Location Provider
- **Sensors**: Accelerometer (Shake Detection)

### **Backend API**
- **Framework**: Spring Boot 3.2.2
- **Language**: Java 17
- **Database**: H2 (dev) / PostgreSQL/MySQL (production)
- **ORM**: Spring Data JPA + Hibernate
- **Build**: Maven
- **Deployment**: Docker-ready with Dockerfile

---

## 🚀 Deployment Guide

### **Option A: Deploy Backend to Render (Free)**

1. **Backend is in**: `app/backend (1)/backend/`

2. **Push to GitHub** (if not already done):
   ```bash
   git add .
   git commit -m "Full-stack NaariRaksha deployment"
   git push origin main
   ```

3. **Deploy on Render**:
   - Go to [render.com](https://render.com) and sign in
   - Click **New+ > Web Service**
   - Connect your **NaariRaksha** repository
   - Configure:
     - **Root Directory**: `app/backend (1)/backend`
     - **Runtime**: Docker
     - **Instance**: Free tier
   - Add environment variable: `PORT=8080`
   - Click **Create Web Service**
   - Get your URL: `https://naariraksha-XXXX.onrender.com`

4. **Update Android App**:
   - Open `app/src/main/java/com/example/naariraksha/data/api/RetrofitClient.kt`
   - Change `BASE_URL` to your Render URL:
     ```kotlin
     private const val BASE_URL = "https://naariraksha-XXXX.onrender.com/"
     ```

### **Option B: Run Locally**

#### Backend
```bash
cd "app/backend (1)/backend"
./mvnw spring-boot:run
# Server runs on http://localhost:8080
```

#### Android App
1. Open project in Android Studio
2. Sync Gradle
3. Run on emulator or device
4. Backend URL is pre-configured as `http://10.0.2.2:8080/` for emulator

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users/register` | Register new user |
| `POST` | `/api/users/login` | User authentication |
| `POST` | `/api/users/sos` | Send emergency SOS |
| `GET` | `/health` | Health check |

---

## 🔧 Configuration

### Backend Environment Variables
- `PORT`: Server port (default: 8080)
- `DB_URL`: Database JDBC URL
- `DB_USER`: Database username
- `DB_PASSWORD`: Database password
- `DB_DRIVER`: JDBC driver class
- `DB_DIALECT`: Hibernate dialect

*Defaults to H2 in-memory database for development*

---

## 📦 Building APK

```bash
# From project root
./gradlew assembleDebug

# APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 🧪 Testing

### Test Backend API
```bash
curl https://your-backend-url.onrender.com/health
# Expected: {"status":"UP"}
```

### Postman Collection
Import and test these endpoints:
- Register: `POST /api/users/register`
- Login: `POST /api/users/login`
- SOS: `POST /api/users/sos`

---

## 📝 Notes

- **First Request Delay**: Free tier backends sleep after inactivity (~30s to wake)
- **Emulator Networking**: Use `10.0.2.2` instead of `localhost` for emulator
- **Production Database**: Configure PostgreSQL on Render for persistent data
- **APK Signing**: Use Android Studio for signed release builds

---

## 🔗 Links

- **Repository**: https://github.com/anjalibahukhandi3/NaariRaksha
- **Render Dashboard**: https://dashboard.render.com
- **Issues**: https://github.com/anjalibahukhandi3/NaariRaksha/issues

---

*Built with ❤️ for safety, empowerment, and elegance.*
