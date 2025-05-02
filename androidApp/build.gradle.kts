plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 23
        targetSdk = 35
        versionCode = 3
        versionName = "1.3"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)

    implementation(libs.picasso)

    implementation(libs.androidx.core.ktx.v160)
    implementation(libs.androidx.dynamicanimation)
}