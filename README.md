# ReydixPokemons

_ReydixPokemons_ is a modular Android application written in Kotlin, designed as a sample architecture and feature showcase for networked, testable, multi-feature Android projects. It uses Jetpack, Kotlin Coroutines, Hilt (for dependency injection), Retrofit, and incorporates MVI (Model-View-Intent) design patterns. The project demonstrates best practices for clean architecture, modularization, and easy testability.

---

## 🌟 Features

- **Modular Structure:** Cleanly separated modules for domain, data, network, resources, and individual features.
- **Network Layer:** Retrofit-based networking with support for real and mock APIs (using OkHttp interceptors).
- **Domain Layer:** Business logic and interactors, including flows for events and Pokémon data.
- **Repository Pattern:** Abstracted data sources with dependency injection for real/mock swapping.
- **ViewModels (MVI):** Unidirectional data flow with clearly defined ViewState, ViewEvent, and SideEffect.
- **Testing:** Includes unit tests for API and repository layers, uses MockWebServer for HTTP layer tests.
- **Resource Management:** Centralized resource provider for strings and other app resources.

---

## 🏗️ Project Structure

```
Reydix/
├── app
│
├── core/    
│   ├── common/         # Shared utilities and extensions
│   ├── data/           # Repository implementations
│   ├── domain/         # Business logic and interactors
│   ├── network/        # Networking: Retrofit, OkHttp, DI modules
│   ├── resources/      # Resource providers (strings, etc.)
│   └── tests/          # Tests for core elements
├── feature/
│   ├── main_screen/    # Main screen UI and ViewModel (MVI)
│   ├── details_screen/ # Pokémon details feature
│   └── tests/          # Tests for feature elemeents
│
├── navigation          # Navigation module       
│ 

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Giraffe or newer recommended)
- JDK 17+
- Gradle 7+

### Clone the Repository

```bash
git clone https://github.com/GeoCoo/Reydix.git
cd Reydix
```

### Build and Run

Open the project in Android Studio and run on an emulator or device.

Or via command line:
```bash
./gradlew assembleDebug
./gradlew installDebug
```

---

## 🔌 Network Configuration

- **Real API:** The app uses Retrofit to fetch real Pokémon .
- **Mock API:** For events cause there is no endpoint, the `MockApiInterceptor` serves static responses from assets.

---

## 🧪 Testing

Run unit core and feature unit tests
```bash
./gradlew :core:tests:testDebugUnitTest
./gradlew :feature:tests:testDebugUnitTest
```
---

## 🛠️ Main Technologies

- **Kotlin**
- **Jetpack (ViewModel, LiveData)**
- **Retrofit, OkHttp, Gson**
- **Dagger-Hilt**
- **Coroutines, Flow**
- **JUnit, MockWebServer**

---

## 🚀 Fastlane Integration

Fastlane fastlane firebase_test_release.
It cleans -> it runs the tests - > create the apk -> distribute to firebase and notify the "testers"

- **It cleans ->**
- **It runs the tests ->**
- **Create the apk ->**
- **Distribute to firebase and notify the "testers"**

via command line:
```bash
fastlane firebase_test_release
```

---

## 🚀 Apk

Apk available to download at
```bash
    https://drive.google.com/file/d/1kZpYqcbsk_xpJL6LneN6HwTDfkhaI8NJ/view?usp=share_link
```

---

---

## 👤 Author

- [GeoCoo](https://github.com/GeoCoo)

---
