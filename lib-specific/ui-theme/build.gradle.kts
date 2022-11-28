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
    api(Deps.AndroidX.Compose.Material.material)
    api(Deps.AndroidX.Compose.Material.materialIconsCore)
    api(Deps.AndroidX.Compose.Material.materialIconsExtended)
    api(Deps.AndroidX.Compose.Ui.ui)
    api(Deps.AndroidX.Compose.Ui.uiTooling)
    api(Deps.AndroidX.Compose.Ui.uiToolingPreview)
}