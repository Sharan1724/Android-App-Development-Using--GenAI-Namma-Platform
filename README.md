# NammaPlatform — Android App

> Smart Railway Platform Navigation and Passenger Assistance Application.  
> Built using Android Studio with Firebase integration for real-time railway information and passenger support.

---

## Architecture

```text
NammaPlatform/
├── app/
│   ├── google-services.json          ← Firebase config (replace with yours)
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/namma/platform/
│           ├── MainActivity.kt
│           ├── NammaPlatformApp.kt   ← Application class / dependency setup
│           ├── data/
│           │   ├── model/
│           │   │   └── Models.kt     ← Train, Station, User, Alerts models
│           │   └── repository/
│           │       ├── AuthRepository.kt
│           │       ├── TrainRepository.kt
│           │       ├── StationRepository.kt
│           │       ├── NotificationRepository.kt
│           │       └── LocationRepository.kt
│           └── ui/
│               ├── NammaNavGraph.kt  ← Navigation management
│               ├── theme/Theme.kt
│               ├── auth/             ← Login/Register screens
│               ├── home/             ← Dashboard & search
│               ├── trains/           ← Live train status
│               ├── stations/         ← Platform & station info
│               ├── navigation/       ← Coach and platform navigation
│               └── alerts/           ← Passenger notifications
```

---

## Setup

### 1. Firebase

1. Go to https://console.firebase.google.com
2. Create a new Firebase project
3. Add an Android app with package name:

```text
com.nammaplatform.app
```

4. Download `google-services.json`
5. Replace the file inside:

```text
app/google-services.json
```

6. Enable:
- Firebase Authentication
- Cloud Firestore
- Firebase Cloud Messaging

---

### 2. API Configuration

Create or update `local.properties`

```properties
TRAIN_API_KEY=your_api_key_here
MAPS_API_KEY=your_maps_api_key
```

---

### 3. Open in Android Studio

- Open the `NammaPlatform/` folder in Android Studio
- Let Gradle sync
- Run on emulator or physical device (API 26+)

---

## Feature Map (Web → Android)

| Feature | Android Equivalent |
|---|---|
| User Authentication | Firebase Auth |
| Train Search | TrainRepository |
| Live Train Status | Real-time API Integration |
| Platform Navigation | Google Maps + Navigation Module |
| Station Details | Firestore + API Data |
| Passenger Alerts | Firebase Cloud Messaging |
| Coach Position Tracking | Dynamic Train Layout UI |
| Material UI Design | Jetpack Compose + Material 3 |

---

## Security Rules

The application uses Firebase Authentication and Firestore security rules to ensure secure access to user-specific data and train-related services.

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {

    match /users/{userId} {
      allow read, write: if request.auth != null
                           && request.auth.uid == userId;
    }

    match /stations/{stationId} {
      allow read: if request.auth != null;
    }

    match /trains/{trainId} {
      allow read: if request.auth != null;
    }
  }
}
```

---

## Dependencies

| Library | Purpose |
|---|---|
| Firebase Auth KTX | Authentication |
| Firebase Firestore KTX | Database |
| Firebase Cloud Messaging | Push Notifications |
| Jetpack Compose + Material 3 | UI |
| Navigation Compose | Screen Navigation |
| Retrofit | API Calls |
| Gson Converter | JSON Parsing |
| Google Maps SDK | Platform Navigation |
| Coil | Image Loading |
| Coroutines | Asynchronous Tasks |
| Room Database | Local Offline Storage |

---

## Future Enhancements

- AI-based crowd prediction
- QR-based platform navigation
- Voice assistant support
- Multi-language support
- Offline station maps
- Smart passenger alerts

---

## Developed Using

- Android Studio
- Kotlin
- Firebase
- Jetpack Compose
- MVVM Architecture
- REST APIs
- Google Maps SDK
