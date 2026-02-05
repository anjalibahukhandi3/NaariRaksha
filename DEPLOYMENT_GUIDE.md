# 📤 Backend Deployment Guide

## Step 1: Push Code to GitHub

### Option A: Using the Script (Easiest)
1. Open File Explorer and navigate to: `e:\NaariRaksha\app\backend (1)\backend`
2. Double-click `deploy_to_github.bat`
3. If prompted for Git credentials, log in with your GitHub account

### Option B: Manual Commands
Open a terminal in `e:\NaariRaksha\app\backend (1)\backend` and run:

```cmd
git init
git remote add origin https://github.com/anjalibahukhandi3/NaariRaksha-backend.git
git add .
git commit -m "Initial backend deployment with Docker"
git branch -M main
git push -u origin main
```

---

## Step 2: Deploy to Render

### 2.1 Create Render Account
1. Go to [render.com](https://render.com)
2. Sign up or log in with GitHub

### 2.2 Create New Web Service
1. Click **"New +"** button in the top right
2. Select **"Web Service"**
3. Click **"Build and deploy from a Git repository"**
4. Click **"Connect account"** to link your GitHub

### 2.3 Select Repository
1. Find and click **"Connect"** next to `NaariRaksha-backend`
2. If you don't see it, click "Configure account" to grant access

### 2.4 Configure Service
Fill in these details:

- **Name**: `naariraksha-backend`
- **Branch**: `main`  
- **Root Directory**: Leave empty
- **Runtime**: **Docker** (Render will auto-detect the Dockerfile)
- **Region**: Choose closest to you (Singapore, Frankfurt, etc.)
- **Instance Type**: **Free**

### 2.5 Environment Variables (Optional)
Click **"Advanced"** and add:
- `PORT` = `8080`

*Note: For now, it will use H2 in-memory database. You can add PostgreSQL later.*

### 2.6 Deploy
1. Click **"Create Web Service"**
2. Wait 3-5 minutes for build and deployment
3. Your URL will be: `https://naariraksha-backend.onrender.com`

---

## Step 3: Test Your API

Once deployed, test with:
```
https://naariraksha-backend.onrender.com/health
```

You should see: `{"status":"UP"}`

---

## Step 4: Update Android App

1. Open `e:\NaariRaksha\app\src\main\java\com\example\naariraksha\data\api\RetrofitClient.kt`
2. Change line 10 from:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/"
   ```
   to:
   ```kotlin
   private const val BASE_URL = "https://naariraksha-backend.onrender.com/"
   ```
3. Save the file

---

## 📝 Notes

- **First request may be slow**: Free tier on Render "sleeps" after inactivity. First request wakes it up (~30 seconds).
- **Persistent Database**: To add PostgreSQL, go to Render Dashboard > New PostgreSQL, then add connection URL to environment variables.
- **Logs**: View logs in Render Dashboard > Your Service > Logs

---

## 🔗 Useful Links

- Your Backend Repo: https://github.com/anjalibahukhandi3/NaariRaksha-backend
- Render Dashboard: https://dashboard.render.com
- Render Docs: https://render.com/docs/deploy-spring
