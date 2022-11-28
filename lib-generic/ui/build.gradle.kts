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

}