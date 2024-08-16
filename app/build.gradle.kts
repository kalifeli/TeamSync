plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleGmsGoogleServices)
    id("org.jetbrains.dokka") version "1.9.20"
}

android {
    namespace = "com.example.teamsync"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.teamsync"
        minSdk = 23
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Splash API
    implementation(libs.androidx.core.splashscreen)
    implementation (libs.coil.compose)
    //Navigation Compose
    implementation(libs.androidx.navigation.compose)
    //Material3
    implementation(libs.material3)


    implementation(libs.ui)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.coil.kt.coil.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.accompanist.permissions)


    // Splash API
    implementation(libs.androidx.core.splashscreen)

    implementation (libs.androidx.material)
    implementation (libs.ui.tooling)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Lottie Animation
    implementation (libs.lottie.compose)

    // Dependencies per il ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    // Dependencies per il test
    testImplementation (libs.junit)
    testImplementation (libs.mockito.core.v3112)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.mockk)
    testImplementation(libs.mockk.v1135)
    testImplementation(libs.objenesis)


    // Espresso per Android Instrumentation tests (androidTest)
    androidTestImplementation (libs.androidx.espresso.core.v340)
    androidTestImplementation (libs.androidx.junit.v121)

}
tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))

    dokkaSourceSets.configureEach {
        if (name == "main") {
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)
            reportUndocumented.set(true)
        }
    }
}
