plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.apollo)
    kotlin("kapt")
}

android {
    namespace = "com.reza.common"
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

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}

apollo {
    service("service") {
        packageName.set("com.reza")

        introspection {
            endpointUrl.set("https://countries.trevorblades.com/graphql")
            schemaFile.set(file("src/main/graphql/com/reza/schema.graphqls"))
        }

        // Enable data builder generation
        generateDataBuilders.set(true)
    }
}