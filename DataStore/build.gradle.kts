import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.troyli-dev"
            artifactId = "datastore"
            version = project.version as String? ?: "unspecified"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

android {
    namespace = "com.rui.datastore"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_11.target
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}