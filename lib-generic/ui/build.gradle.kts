plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.KOTLIN_ANDROID)
}

android {
    namespace = "com.sc941737.lib.ui"

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        compileSdk = AndroidConfig.COMPILE_SDK_VERSION
    }
}

dependencies {
    api(Deps.AndroidX.Activity.activityKtx)
    api(Deps.AndroidX.Lifecycle.lifecycleViewmodelKtx)
    api(Deps.JetBrains.KotlinX.Coroutines.kotlinxCoroutinesCore)
    api(Deps.JetBrains.KotlinX.Coroutines.kotlinxCoroutinesAndroid)
}