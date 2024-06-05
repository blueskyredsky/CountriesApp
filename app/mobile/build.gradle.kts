import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
    id("com.apollographql.apollo3") version "3.8.2"
    id("kotlin-parcelize")
}

android {
    namespace = "com.reza.mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reza.countriesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.reza.countriesapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        if (rootProject.file("../CountriesApp/signing/release.props").exists()) {
            val signingRelease = Properties()
            signingRelease.load(FileInputStream(rootProject.file("../CountriesApp/signing/release.props")))
            create("release") {
                storeFile = rootProject.file("../CountriesApp/signing/release.keystore")
                storePassword = signingRelease.getProperty("storePass")
                keyAlias = signingRelease.getProperty("keyAlias")
                keyPassword = signingRelease.getProperty("keyPass")
            }
        } else {
            create("release") {
                keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
                keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
                storeFile = file(System.getenv("HOME") + "/keystores/project_release.keystore")
                storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // To access resources file in test package
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    // To access assets in androidTest package
    sourceSets {
        getByName("androidTest").assets.srcDirs("src/debug/assets")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += "environment"
    productFlavors {
        create("demo") {
            dimension = "environment"
            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
        }
        create("production") {
            dimension = "environment"
            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
        }
    }
}

dependencies {

    // Projects
    implementation(project(":feature:home"))
    implementation(project(":feature:details"))
    implementation(project(":core:networking"))
    implementation(project(":core:threading"))
    testImplementation(project(":core:testing:unit"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.activity.compose)
    implementation(libs.material3)
    implementation(libs.material)
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    // ViewModel
    implementation(libs.viewmodel.compose)

    // Navigation
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
    implementation(libs.hilt.navigation.compose)

    // Apollo
    implementation(libs.apollo)

    // OkHttpLogging
    implementation(libs.okhttp.logging)

    // Timber
    implementation(libs.timber)

    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.hilt.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.navigation.test)
    kaptAndroidTest(libs.hilt.kapt.test)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apollo {
    service("service") {
        packageName.set("com.reza")

        // Enable test builder generation
        generateTestBuilders.set(true)
    }
}