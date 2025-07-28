plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.reza.unit"
    compileSdk = 36

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

    packaging {
        resources {
            pickFirsts.add("META-INF/LICENSE.md")
        }
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    // Test
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.turbine)
    api(libs.mockito.nhaarman)
    api(libs.mockito)
    api(libs.mockk)
    api(libs.truth)
    api(libs.apollo.test)
    api(libs.apollo.mock.server)
    debugApi(libs.ui.test.manifest)
}