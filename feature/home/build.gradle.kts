plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    kotlin("kapt") // todo change that to alias
    id("kotlin-parcelize") // todo change that to alias
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.reza.feature.home"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.reza.feature.home.HiltTestRunner"
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
    implementation(project(":core:threading"))
    implementation(project(":core:networking"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    testImplementation(project(":core:testing:unit"))
    androidTestImplementation(project(":core:testing:ui"))

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
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}