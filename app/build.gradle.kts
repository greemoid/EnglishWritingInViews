@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.safeargs)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.englishwritinginviews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.englishwritinginviews"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://api.languagetoolplus.com/v2/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            buildConfigField("String", "API_URL", "\"https://api.languagetoolplus.com/v2/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    // Calendar
    implementation(libs.calendar)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kaptTest(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.interceptor)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    // Jetpack Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    androidTestImplementation(libs.navigation.testing)

    // Arch
    implementation(libs.viewmodel)
    testImplementation(libs.arch.testing)

    // Splash Screen
    implementation(libs.splash)

    // MockK
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)


    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

}