plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
}

//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        maven { url = java.net.URI("https://jitpack.io") }
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.1.2")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven { url = java.net.URI("https://jitpack.io") }
//    }
//}
//
//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
