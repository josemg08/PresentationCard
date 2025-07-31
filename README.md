# Android Presentation Card App 📱

![Presentation Card Banner](app/src/main/assets/readme_images/readme_banner.png)

A comprehensive Android sample project designed to teach fundamental Android development concepts through building a practical presentation card application. This project serves as a hands-on learning experience for beginners to understand core Android components and modern development practices.

## 🎯 Project Overview

The **Presentation Card App** is an educational project that demonstrates essential Android development concepts by creating a digital business card application. Students will learn by building a complete app that showcases personal or professional information in an elegant, mobile-friendly format.

## 📚 Learning Objectives

This project teaches the following Android development concepts:

### Core Components
- **Activities & Lifecycle**: Understanding activity states and navigation
- **Layouts & Views**: XML layout design, ViewGroups, and UI components
- **Intents**: Activity navigation and data passing
- **Resources**: Strings, colors, dimensions, and drawable resources

### UI/UX Design
- **Material Design**: Implementing Google's design principles
- **Responsive Layouts**: Creating layouts that work across different screen sizes
- **Navigation Components**: Bottom navigation and activity transitions
- **Custom Styling**: Themes, styles, and custom drawable resources

### Data Management
- **JSON Parsing**: Reading and processing JSON data
- **RecyclerView**: Displaying lists with efficient scrolling
- **Adapters**: Connecting data to UI components
- **Resource Management**: Organizing and accessing app resources

## 🏗️ App Features

### ✨ Core Functionality
- **Profile Display**: Show personal/professional information with photo
- **Education Section**: Display educational background with RecyclerView
- **Social Networks**: Quick access to social media and contact information
- **Material Design**: Modern, clean UI following Material Design guidelines

### 🎨 UI Components Demonstrated
- Circular profile images with ShapeableImageView
- Card-based layouts for information display
- Custom backgrounds with border strokes
- Icon integration and tinting

### 📸 App Screenshots

<div align="center">
   <img src="app/src/main/assets/readme_images/readme_showcase_image_2.png" width="20%">
   <img src="app/src/main/assets/readme_images/readme_showcase_image_1.png" width="60%">
</div>

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Meerkat or later
- Android SDK API level 24 or higher
- Basic understanding of Java
- Git for version control

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/android-presentation-card.git
   cd android-presentation-card
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Sync and Build**
   - Android Studio will automatically sync the project
   - Wait for the build to complete
   - Run the app on an emulator or physical device

## 📖 Learning Path & Project Structure

### Module 1: Android Fundamentals (Branch: main)
#### Project Architecture
- **Project Structure**
  - Android Manifest configuration
  - Gradle build system setup
  - Resource management:
    - Layouts (Portrait/Landscape orientations)
    - Drawables (mipmap, SVG, density-specific resources)
    - Values (colors, strings, multi-language support)
    - Themes & Styles (Light/Dark mode support)
    - XML resource handling

#### UI Development Basics
- **Activities & Views**
  - Activity setup with `setContentView`
  - View binding with `findViewById`
  - View listeners implementation
- **Core UI Components**
  - Buttons and click handling
  - TextViews for display
  - EditText for user input
  - ImageView management
- **Layout Types**
  - LinearLayout usage
  - ScrollView implementation
  - RelativeLayout structuring

### Module 1 Extended: Android Components (Tag: Clase1-3)
- Activity visibility states
- Complete activity lifecycle
- Intent system & navigation
- State management (Bundles, extras, SaveState)
- Advanced Manifest configuration
  - Intent filters
  - MAIN launcher
  - Service declarations
- System Components
  - Content Providers
  - Broadcast Receivers

### Module 2: Responsive Design (Tags: before_dimens, sample_with_SW)
#### Screen Adaptability
- Profile layouts before/after SW implementation
- Smallest Width (SW) values & dimensions
- Advanced Layout Features
  - Custom shapes
  - Ripple effects
  - Layout includes

#### UI Components & Navigation
- FloatingActionButton implementation
- MaterialToolbar integration
- Advanced Intents
  - Deep linking
  - Intent actions
  - Data transmission
- System UI Integration
  - Status bar customization
  - UI mode handling
  - Toast notifications
- Context management

### Module 3: Data & Lists (Tags: simpleRecycler, simplCheckboxOnRecycler, SavingJson)
#### RecyclerView Implementation
- Basic RecyclerView setup
  - LayoutManager configuration
  - Adapter pattern
  - ViewHolder pattern
- Interactive list items with checkboxes

#### Data Management
- JSON asset handling
- EducationItem data modeling
- File operations
  - Reading from assets
  - Error handling
  - Activity result handling
- File Storage Types
  - Internal storage
  - External storage
  - App-specific storage
  - Cache files

### Module 4: App Polish (Tag: dataSavingSamples)
- **Resource Management**
  - Drawable selectors
  - Advanced manifest queries
- **App Architecture**
  - Application class setup
  - Splash screen implementation
  - Welcome flow
- **Data Persistence**
  - SharedPreferences usage
- **Threading & Animation**
  - Handler and Looper
  - Runnable implementations
  - Lottie animations

### Module 5: Advanced Features (Tag: EasterEgg)
- **Interactive Features**
  - Gesture detection
  - MotionEvent handling
  - Device rotation
- **Custom Graphics**
  - Custom View creation
  - Canvas drawing
  - Paint configurations
  - Focus management

---

**Happy Learning! 🚀** Start your Android development journey with this hands-on project and build your first professional presentation card app.
