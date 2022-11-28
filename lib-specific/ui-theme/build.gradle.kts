@file:Suppress("UnstableApiUsage")

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.KOTLIN_ANDROID)
}

android {
    namespace = "com.sc941737.lib.ui_theme"

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        compileSdk = AndroidConfig.COMPILE_SDK_VERSION
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Deps.AndroidX.Compose.Ui.ui)
    implementation(Deps.AndroidX.Compose.Ui.uiTooling)
    implementation(Deps.AndroidX.Compose.Ui.uiToolingPreview)
    implementation(Deps.AndroidX.Compose.Material.material)
}