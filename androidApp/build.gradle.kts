plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    kotlin("android")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.lccdev.litechat"
        minSdk = 23
        targetSdk = 36
        versionCode = 14
        versionName = "1.14"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.picasso)
    implementation(libs.androidx.core.ktx.v160)
    implementation(libs.androidx.dynamicanimation)
    implementation(libs.jackson.databind)
    implementation(libs.androidx.room.runtime)
    implementation (libs.openim.android.sdk)
    implementation (libs.openim.core.sdk)
    implementation (libs.gson)
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)
    implementation(libs.mmkv)
}