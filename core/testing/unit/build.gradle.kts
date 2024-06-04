plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.apollographql.apollo3") version "3.8.2"
}

android {
    namespace = "com.reza.unit"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Apollo
    implementation(libs.apollo)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    // Test
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.turbine)
    api(libs.mockito.nhaarman)
    api(libs.mockito)
    api(libs.truth)
    api(libs.apollo.test)
    api(libs.apollo.mock.server)
    debugApi(libs.ui.test.manifest)
}

apollo {
    service("service") {
        packageName.set("com.reza")

        // Enable test builder generation
        generateTestBuilders.set(true)
    }
}