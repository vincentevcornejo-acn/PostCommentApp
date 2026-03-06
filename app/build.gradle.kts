import org.jetbrains.kotlin.gradle.dsl.JvmTarget
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

val javaVersion = JavaVersion.toVersion(libs.versions.javaVersion.get().toInt())

android {
    namespace = "com.codingchallenge.postcommentapp"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://jsonplaceholder.typicode.com/\""
        )
        applicationId = "com.codingchallenge.postcommentapp"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

hilt {
    enableAggregatingTask = true
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycleRuntimeCompose)
    implementation(libs.androidx.lifecycleViewModelCompose)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // AndroidX Compose
    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.compose.uiToolingPreview)
    debugImplementation(libs.androidx.compose.uiTestManifest)
    implementation(libs.androidx.compose.materialIconsExtended)
    implementation(libs.androidx.compose.materialIconsExtendedAndroid)

    // AndroidX Navigation
    implementation(libs.androidx.navigation.navigationFragmentKtx)
    implementation(libs.androidx.navigation.navigationUiKtx)
    implementation(libs.androidx.navigation.navigationCompose)

    // hilt
    implementation(libs.com.google.dagger.hilt)
    ksp(libs.com.google.dagger.hiltCompiler)
    implementation(libs.androidx.hilt.hiltNavigationCompose)

    // Networking
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.converterGson)
    implementation(libs.com.google.code.gson.gson)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.squareup.okhttp3.loggingInterceptor)

    // Room
    implementation(libs.androidx.room.roomRuntime)
    ksp(libs.androidx.room.roomCompiler)
    implementation(libs.androidx.room.roomKtx)
}