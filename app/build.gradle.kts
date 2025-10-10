import com.android.builder.files.classpathToRelativeFileSet
import org.gradle.kotlin.dsl.provider.inClassPathMode

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gpsmapapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gpsmapapp"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation ("com.google.android.gms:play-services-location:19.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("org.maplibre.gl:android-sdk:10.0.0")
    implementation ("org.jspecify:jspecify:0.3.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}