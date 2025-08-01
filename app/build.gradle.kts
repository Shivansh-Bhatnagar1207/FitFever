plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
        id("kotlin-kapt")
        id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.vitalizeme"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.vitalizeme"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }
    viewBinding{
        enable = true
    }

}

dependencies {
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.29")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.fragment)
    implementation ("com.google.firebase:firebase-auth:24.0.0")
    implementation ("com.google.android.gms:play-services-auth:21.4.0")
    implementation("com.google.firebase:firebase-messaging:25.0.0")
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)

    val paging_version = "3.3.6"
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.firebase:firebase-messaging:25.0.0")
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("com.google.dagger:hilt-android:2.57")
    kapt("com.google.dagger:hilt-android-compiler:2.57")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}