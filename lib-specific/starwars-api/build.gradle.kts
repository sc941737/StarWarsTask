plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.KOTLIN_ANDROID)
    id(GradlePlugin.KOTLIN_SERIALIZATION) version Versions.KOTLIN
}

android {
    namespace = "com.sc941737.lib.starwars_api"
    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        compileSdk = AndroidConfig.COMPILE_SDK_VERSION
    }
    buildTypes.all {
        buildConfigField("String", "baseApiUrl", "\"https://swapi.dev/api\"")
    }
}

dependencies {
    implementation(project(":lib-generic:network"))
    implementation(Deps.Koin.android)
    testImplementation(Deps.JetBrains.Kotlin.kotlinTestJunit)
}