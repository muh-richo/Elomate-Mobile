plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.unitedtractors.elomate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.unitedtractors.elomate"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("androidx.activity:activity-ktx:1.9.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Circle Image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Spider Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Calender
    implementation("com.github.ArjunGupta08:Horizontal-CalendarDate-With-Click-listener:1.1.0")

    // The view calendar library for Android
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")

    // The view calendar library for Android
    implementation("com.kizitonwose.calendar:view:2.5.1")
    implementation ("com.applandeo:material-calendar-view:1.9.2")

    // Nav
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // PDF Viewer
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")

}