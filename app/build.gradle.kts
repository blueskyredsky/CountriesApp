import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.reza.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.reza.countriesapp"
        minSdk = 24
        targetSdk = 36
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

    packaging {
        resources {
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE.txt"
            excludes += "/META-INF/LICENSE.md"
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("demo") {
            dimension = "environment"
            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
            testInstrumentationRunner = "com.reza.countriesapp.HiltTestRunner"
        }
        create("production") {
            dimension = "environment"
            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
            testInstrumentationRunner = "com.reza.countriesapp.HiltTestRunner"
        }
    }
}

dependencies {

    // Projects
    implementation(projects.feature.home)
    implementation(projects.feature.details)
    implementation(projects.core.threading)
    implementation(projects.core.networking)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.testing.ui)
    implementation(projects.core.testing.unit)

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
    testImplementation(libs.apollo.test)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}