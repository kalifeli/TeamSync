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
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/NOTICE.md"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/LICENSE-notice.md"

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
    implementation(libs.androidx.core.splashscreen) // Splash API
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose) // Navigation Compose
    implementation(libs.material3)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling)
    implementation(libs.lottie.compose) // Lottie Animation
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData

    // Test dependencies
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core.v3112)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.v1135)
    testImplementation(libs.objenesis)

    // Instrumented test dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.navigation.testing) // Navigation testing
    // Dipendenze per il test unitario
    testImplementation(libs.mockk.v1132)

    // Dipendenze per i test strumentali
    androidTestImplementation (libs.mockk.android)
    testImplementation(libs.mockito.core.v500)
    androidTestImplementation (libs.mockito.android)
    // Dipendenza per fare mocking di classi final
    testImplementation (libs.mockito.inline)
    androidTestImplementation(libs.testng)


    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dipendenza necessaria per InstantTaskExecutorRule
    testImplementation (libs.androidx.core.testing)
}

tasks.dokkaHtml {
    outputDirectory.set(layout.projectDirectory.dir("Documentazione/Html"))

    dokkaSourceSets.configureEach {
        if (name == "main") {
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)
            reportUndocumented.set(true)
        }
    }
}

tasks.dokkaGfm {
    outputDirectory.set(layout.projectDirectory.dir("Documentazione/Gfm"))

    dokkaSourceSets.configureEach {
        if (name == "main") {
            includeNonPublic.set(false) // Non include membri non pubblici
            skipEmptyPackages.set(true) // Salta i pacchetti vuoti
            reportUndocumented.set(true) // Riporta i membri non documentati
        }
    }
}
