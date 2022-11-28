@file:Suppress("UnstableApiUsage")

plugins {
    id(GradlePlugin.ANDROID_APPLICATION)
    id(GradlePlugin.KOTLIN_ANDROID)
    id(GradlePlugin.KSP) version Versions.KSP
}

android {
    namespace = "com.sc941737.starwarstask"
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.sc941737.starwarstask"
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION
        // todo: This should normally be extracted to separate file for CI scripts
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        // todo: This should normally be extracted to a separate file
        val demo by creating {
            keyAlias = "key0"
            keyPassword = "123456"
            storeFile = file("path/to/star_wars_keystore.jks") // Change to match local file path
            storePassword = "123456"
        }
    }
    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("demo")
        }
    }
    compileOptions {
        sourceCompatibility = Versions.JAVA_VERSION
        targetCompatibility = Versions.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Versions.JAVA_VERSION_STR
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.version
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":lib-generic:error"))
    implementation(project(":lib-generic:network"))
    implementation(project(":lib-generic:ui"))
    implementation(project(":lib-specific:starwars-api"))
    implementation(project(":lib-specific:ui-theme"))
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.Lifecycle.lifecycleProcess)
    implementation(Deps.AndroidX.Lifecycle.lifecycleRuntime)
    implementation(Deps.AndroidX.Activity.activityCompose)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.androidCompose)
    implementation(Deps.Raamcosta.ComposeDestinations.core)
    ksp(Deps.Raamcosta.ComposeDestinations.ksp)
    testImplementation(Deps.Test.JUnit.junit4)
    testImplementation(Deps.Test.Ext.junitKtx)
    androidTestImplementation(Deps.Test.Espresso.espressoCore)
    androidTestImplementation(Deps.AndroidX.Compose.Ui.uiTestJunit4)
    debugImplementation(Deps.AndroidX.Compose.Ui.uiTestManifest)
}