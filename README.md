📝 NoteIt – Offline-First Notes & Todo Application (Android)

NoteIt is a modern, offline-first Notes and Todo management application built using Kotlin and Jetpack Compose, following MVVM architecture with Clean Architecture principles.
The app is designed to be scalable, lifecycle-safe, and production-ready, with advanced editor features, home-screen widgets, reminders, and a fully reactive UI.

🚀 *Key Features* - 
-

## 📝 Notes Management
- Create, edit, and delete notes
- Rich note editor with:
    Highlightable text support
    Custom note editor toolbar
- Clear separation of title and content
- Text processing & formatting utilities
- Unsaved changes detection with user confirmation dialog
- Swipe-to-dismiss gesture support
- Offline-first note persistence using local database

## ✅ Todo Management
- Create and manage todo items
- Custom circular checkbox UI component
- Date & time picker integration for todos
- Bottom action bar for quick actions
- Persistent todo storage using Room
- Reminder scheduling for time-based todos

## ⏰ Reminders & Notifications
- Time-based reminders for todo items
- System-reliable scheduling using:
      ReminderScheduler
      BroadcastReceiver
- Notification handling via a dedicated DI module
- Designed to survive:
   App restarts
   Device reboots

## 🏠 Home Screen Widgets
- Notes Widget
    Displays notes directly on the home screen
- Todo Widget
    Displays theme adaptive and interacts with todo items

- Widget capabilities:
    Dedicated widget UI and content providers
    Broadcast receivers for widget actions
    Direct database access for live data updates
    Light & Dark theme support

## 🎨 UI & UX
- 100% Jetpack Compose–based UI
- Material Design 3 (Material You)
- Custom Light & Dark color palettes
- Glassmorphism-inspired UI components
- Dynamic Floating Action Button (FAB)
- Bottom navigation bar
- Smooth animations and responsive layouts

## 🧠 Architecture & Android Development Practices

# 📐 Architecture
- MVVM (Model–View–ViewModel)
- Clean Architecture–inspired layering:
- data – database, DAOs, entities
- ui – screens, components, navigation, widgets
- di – dependency injection setup
- Repository-driven data access
- Single source of truth for UI state

# 🔄 Reactive & Lifecycle-Aware
- Kotlin Coroutines for asynchronous operations
- Kotlin Flow for reactive data streams
- Lifecycle-aware state collection in Jetpack Compose
- Efficient recomposition handling for performance

# 🗄️ Local Data Storage
- Room Database for local persistence
- Separate entities and DAOs for:
    Notes
    Todos
- Database migrations supported
- Offline-first design with no network dependency

# 🧩 Dependency Injection
- Hilt for dependency management
- Dedicated DI modules:
    DatabaseModule
    NotificationModule
- Proper scoping for application-level and feature-level dependencies

# 🧭 Navigation
- Jetpack Navigation (Compose)
- Centralized navigation graph
- Typed routes for maintainability
- Scalable navigation architecture

# ⚙️ App & System Integration
- Custom Application class
- Compose-based MainActivity
- Backup and data extraction rules configured
- Adaptive launcher icons (Light & Dark)
- Widget metadata defined via XML

# 🛠️ Tech Stack

| Category | Technology |
|---------|------------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Database | Room |
| Async | Kotlin Coroutines |
| Reactive | Kotlin Flow |
| Dependency Injection | Hilt |
| Navigation | Jetpack Navigation (Compose) |
| Widgets | App Widgets |
| Notifications | Alarm / BroadcastReceiver |
| Design System | Material Design 3 |



## 📂 Project Structure (Simplified)

com.ashraf.notes
│
├── data
│   ├── local
│   │   ├── note (Entity, DAO)
│   │   ├── todo (Entity, DAO)
│   │   └── NotesDatabase
│   ├── di
│   └── notification
│
├── ui
│   ├── home
│   ├── notes
│   ├── todo
│   ├── components
│   ├── navigation
│   ├── widget
│   └── theme
│
├── worker / receiver
│
├── MainActivity
└── NotesApp

# 🧪 Android Best Practices Followed
- Offline-first data strategy
- Lifecycle-safe state management
- Immutable UI state
- Modular and reusable Compose components
- Separation of concerns
- Production-ready widget & notification handling

# 🔮 Future Enhancements
- Cloud sync & multi-device support
- Note sharing(Pdf, image, txt) & collaboration 
- Search, filters, and tags
- Markdown support
- Biometric-secured notes

# 👨‍💻 Author

# Ashraf Ali
Android Application Developer
Focused on scalable architecture, modern UI, and real-world Android systems
