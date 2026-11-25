# KarobarV2 - Laravel + React + Sneat Theme Authentication

A full-stack authentication system built with **Laravel (API Backend)**, **React + Vite (Frontend)**, **MySQL (Database)**, and styled with the **Sneat Bootstrap 5 Admin Theme**.

---

## 📁 Project Structure

```text
KarobarV2/
├── backend/                  # Laravel 12 API backend
│   ├── app/Http/Controllers/Api/AuthController.php   # Register, Login, User, Logout
│   ├── routes/api.php        # API routes: /api/register, /api/login, /api/user, /api/logout
│   ├── config/cors.php       # CORS configuration for React frontend
│   └── .env                  # MySQL DB configuration (database: karobar_v2)
│
├── frontend/                 # React 19 + Vite frontend
│   ├── public/assets/        # Complete Sneat theme assets (CSS, Fonts, Boxicons, Images)
│   ├── src/
│   │   ├── api/axios.js      # Axios instance with auth token interceptors
│   │   ├── context/AuthContext.jsx # Global auth state (user, token, login, register, logout)
│   │   ├── pages/
│   │   │   ├── Login.jsx     # Sneat-themed Login page
│   │   │   ├── Register.jsx  # Sneat-themed Registration page
│   │   │   └── Dashboard.jsx # Authenticated user dashboard & live API test
│   │   └── components/
│   │       ├── SneatLogo.jsx
│   │       └── ProtectedRoute.jsx
│   └── vite.config.js        # Vite config with API proxy
│
├── android-app/              # Native Kotlin Android Application
│   ├── app/src/main/
│   │   ├── java/com/karobar/app/
│   │   │   ├── security/SecurityUtils.kt     # Root, Debugger, Emulator & Anti-tamper checks
│   │   │   ├── ui/splash/SplashActivity.kt   # 4-sec full-screen video splash + fade transition
│   │   │   └── ui/main/MainActivity.kt       # Hello World Home Screen (Plus Jakarta Sans)
│   │   └── res/
│   │       ├── raw/splash_screen.mp4         # 4-second splash video
│   │       └── font/plus_jakarta_sans.ttf    # Plus Jakarta Sans brand typography
│   └── app/proguard-rules.pro                # R8/ProGuard anti-reverse engineering rules
│
└── sneat-1.0.0/              # Original Sneat theme template files
```

---

## 🚀 How to Run the Project

### 1. Database
Make sure MySQL is running in XAMPP.
- **Database Name:** `karobar_v2`
- **Host:** `127.0.0.1:3306`
- **Username:** `root`
- **Password:** ` ` *(empty)*

### 2. Backend (Laravel API)
Open a terminal in the `backend` folder:
```bash
cd backend
php artisan serve
```
Backend runs on: **`http://127.0.0.1:8000`**

### 3. Frontend (React Vite)
Open a second terminal in the `frontend` folder:
```bash
cd frontend
npm run dev
```
Frontend runs on: **`http://127.0.0.1:5173`**

### 4. Android App (Kotlin Native)
To build the Android APK:
```bash
cd android-app

# Build Debug APK
./gradlew assembleDebug

# Build Production Obfuscated Release APK (Protected with ProGuard/R8)
./gradlew assembleRelease
```
APKs generated in: `android-app/app/build/outputs/apk/`

---

## 🔑 Features

- **Sneat Theme Integration:** Exact styling, Boxicons, typography, form controls, and layout from Sneat.
- **User Registration:**
  - Username, Email, Password, Terms agreement checkbox.
  - Password visibility toggle (eye icon).
  - Instant validation & error alerts.
- **User Login:**
  - Login using **either Email or Username** (as specified in Sneat theme).
  - Password visibility toggle.
  - Remember Me option.
  - Bearer token authentication via Laravel Sanctum stored in `localStorage`.
- **Protected Dashboard:**
  - Displays authenticated user profile.
  - Live "Test Auth API (`/api/user`)" button.
  - Logout functionality that revokes the token and redirects to `/login`.
