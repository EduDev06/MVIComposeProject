plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.22"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mvicomposeproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvicomposeproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val hilt = "2.50"
    val hiltNavigation = "1.2.0"
    val kotlinSerialization = "1.6.2"
    val okHttp = "4.12.0"
    val retrofit = "2.9.0"
    val jakewharton = "1.0.0"
    val room = "2.6.1"
    val runtimeCompose = "2.7.0"
    val testCoroutine = "1.7.3"
    val googleTruth = "1.1.5"
    val mockk = "1.13.10"
    val turbine = "1.0.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt")
    ksp("com.google.dagger:hilt-android-compiler:$hilt")

    // Navigation hilt
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigation")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerialization")

    // okHttp
    implementation("com.squareup.okhttp3:okhttp:$okHttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$jakewharton")

    // Room
    implementation("androidx.room:room-runtime:$room")
    ksp("androidx.room:room-compiler:$room")
    implementation("androidx.room:room-ktx:$room")

    // Runtime-Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$runtimeCompose")

    // Test Coroutine
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$testCoroutine")

    // Google Truth
    testImplementation("com.google.truth:truth:$googleTruth")

    // MockK
    testImplementation("io.mockk:mockk:$mockk")

    // Turbine
    testImplementation("app.cash.turbine:turbine:$turbine")
}