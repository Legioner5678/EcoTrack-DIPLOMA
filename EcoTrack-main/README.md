# 🌍 EcoTrack: Intelligent System for Supporting an Environmentally Conscious Lifestyle

**EcoTrack** is a sophisticated Android application engineered to promote and sustain an environmentally conscious lifestyle. By integrating habit tracking, real-time data analysis, and gamification, the app empowers users to minimize their ecological footprint and contribute to global sustainability.

This project serves as a comprehensive capstone for the Android Development course and provides the architectural foundation for a **Diploma Thesis**.

---

## 🚀 Key Features

### 📡 Real-Time Environmental Intelligence
* **Global Eco-News Feed:** Integration with NewsAPI to deliver categorized updates on Climate, Energy, Oceans, and Green Innovations.
* **Smart Category Filtering:** Dynamic content sorting using a "Google News" style UI with custom FilterChips.
* **Image Integration:** Visual news presentation powered by the Coil library for enhanced user engagement.

### 🌱 Intelligent Habit Management
* **Eco-Habit Tracker:** Log daily activities such as recycling, energy saving, and sustainable commuting.
* **Gamification Engine:** Reward system based on **Eco-Points**, leveling, and daily **Streaks** to maintain consistency.

### 📊 Personal Analytics & Profile
* **Historical Data:** Comprehensive history of ecological contributions stored locally via Room DB.
* **User Personalization:** Customizable user profiles with location-based settings managed through Jetpack DataStore.

---

## 🛠 Technical Stack

### 🏗 Architecture & Design
* **MVVM (Model-View-ViewModel):** Strict separation of concerns for testability and maintenance.
* **Clean Architecture Principles:** Organized package structure for scalable development.
* **Repository Pattern:** Centralized data access abstraction.

### 💻 Core Technologies
* **Language:** Kotlin (100%)
* **UI Framework:** Jetpack Compose (Declarative UI)
* **Dependency Injection:** Hilt (Dagger)
* **Asynchrony:** Kotlin Coroutines & Flow for reactive state management.

### 🌐 Data & Persistence
* **Networking:** Retrofit 2 & OkHttp 3 with Logging Interceptor.
* **Local Database:** Room Persistence Library (SQL-based habit and points storage).
* **Key-Value Storage:** Jetpack DataStore (Modern Preferences management).

---

## 👥 Development Team
* Augambaev Alen — Lead Android Developer / Software Architect
* Almerek Aitzhan — UI/UX Designer / Data Engineer

---

## 📁 Project Structure

```text
com.example.ecotrack/
│
├── data/
│   ├── database/          # Room DB, DAOs, Entities (EcoPointEntity)
│   └── network/           # Retrofit interfaces, API Models (DTOs)
│
├── domain/                # Business logic and domain models
│
├── ui/
│   ├── screens/
│   │   ├── home/          # News Feed & Category Filtering
│   │   ├── analytics/     # Habit Statistics & Charts
│   │   ├── profile/       # User Profile & Habit History
│   │   └── settings/      # App Preferences
│   └── theme/             # Material3 Design System (Color, Theme, Type)
│
├── viewmodel/             # ViewModels (Home, Profile, Analytics, Settings)
└── utils/                 # Helpers (UserPreferences, DateUtils, FormatUtils)

