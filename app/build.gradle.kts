plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.greenbite"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.greenbite"
        minSdk = 24
        targetSdk = 35
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
        dataBinding = true
    }
}

dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.androidx.databinding.runtime)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    implementation(libs.androidx.room.runtime)
//    kapt(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.ktx)
//
//    implementation (libs.retrofit)
//    implementation (libs.okhttp)
//    implementation (libs.moshi)
//    implementation (libs.moshi.kotlin)
//    kapt(libs.moshi.kotlin.codegen)
//    implementation (libs.picasso)
//
//    implementation("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
//
//    implementation("com.squareup.moshi:moshi:1.15.2")
//    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
//    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)

    //fragment
    implementation (libs.androidx.fragment.ktx)

    //view model
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v270)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    implementation(libs.androidx.activity.ktx)

    // room db (local storage)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    //connect to API (retrofit)
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)
    kapt(libs.moshi.kotlin.codegen)
    implementation (libs.picasso)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.activity:activity-ktx:1.10.1")

    implementation("androidx.room:room-runtime:2.7.0")
    implementation("androidx.room:room-ktx:2.7.0")
    kapt("androidx.room:room-compiler:2.7.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.2")
}