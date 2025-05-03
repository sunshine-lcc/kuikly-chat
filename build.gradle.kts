plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.9.1").apply(false)
    id("com.android.library").version("8.9.1").apply(false)
    kotlin("android").version("2.1.20").apply(false)
    kotlin("multiplatform").version("2.1.20").apply(false)
    id("com.google.devtools.ksp").version("2.1.20-2.0.1").apply(false)
}
