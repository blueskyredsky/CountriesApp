plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.reza.details"
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    // projects
    implementation(projects.core.threading)
    implementation(projects.core.networking)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.testing.ui)
    implementation(projects.core.testing.unit)

    implementation(libs.javax.inject)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.google.material)

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.activity.compose)
    implementation(libs.compose.lifecycle)
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.navigation.compose)
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
    implementation(libs.hilt.navigation.compose)

    // serialisation
    implementation(libs.kotlinx.serialization.json)

    // ui test
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.hilt.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.navigation.test)
    androidTestImplementation(libs.androidx.runner)
    kaptAndroidTest(libs.hilt.kapt.test)

    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
}