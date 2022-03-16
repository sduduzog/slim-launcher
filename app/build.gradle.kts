plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.sduduzog.slimlauncher"
        minSdk = 21
        targetSdk = 31
        versionName = "2.4.21"
        versionCode = 55
        vectorDrawables { useSupportLibrary = true }
        signingConfigs {
            if (project.extra.has("RELEASE_STORE_FILE")) {
                register("release") {
                    storeFile = file(project.extra["RELEASE_STORE_FILE"] as String)
                    storePassword = project.extra["RELEASE_STORE_PASSWORD"] as String
                    keyAlias = project.extra["RELEASE_KEY_ALIAS"] as String
                    keyPassword = project.extra["RELEASE_KEY_PASSWORD"] as String
                }
            }
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        named("release").configure {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.maybeCreate("release")
        }
        named("debug").configure {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin Libraries
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.21")

    // Support Libraries
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // Arch Components
    implementation("androidx.core:core-ktx:1.7.0-beta01")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    kapt("androidx.room:room-compiler:2.4.2")

    //3rd party libs
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation("com.google.dagger:hilt-android:2.41")
    kapt("com.google.dagger:hilt-compiler:2.41")


    // Unit test libs
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.robolectric:robolectric:4.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("com.google.dagger:hilt-android-testing:2.41")
    kaptTest("com.google.dagger:hilt-android-compiler:2.41")

    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
}
kapt {
    correctErrorTypes = true
}