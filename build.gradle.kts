//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.1.3")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
//    }
//}
//
//tasks.register("clean",Delete::class){
//    delete(rootProject.buildDir)
//}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("com.android.library") version "8.0.0" apply false
    kotlin("android") version "1.8.22" apply false
}