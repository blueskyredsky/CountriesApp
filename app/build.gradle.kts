plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.apollographql.apollo3") version "3.8.2"
    id("kotlin-parcelize")
}

android {
    namespace = "com.reza.countriesapp"
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

    buildTypes {
        getByName("release") {
            //isMinifyEnabled = true
            //isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(":base"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    testImplementation("junit:junit:4.12")

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.activity.compose)
    implementation(libs.material3)
    implementation(libs.material)
    debugImplementation(libs.ui.tooling)

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

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.mockito.nhaarman)
    testImplementation(libs.mockito)
    testImplementation(libs.truth)
    testImplementation(libs.apollo.test)
    testImplementation(libs.apollo.mock.server)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.hilt.test)
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