import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(GradlePlugin.ANDROID_APPLICATION) version Versions.ANDROID_PLUGIN apply false
    id(GradlePlugin.ANDROID_LIBRARY) version Versions.ANDROID_PLUGIN apply false
    id(GradlePlugin.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(GradlePlugin.KOTLIN_JVM) version Versions.KOTLIN
}
dependencies {
    implementation(Deps.JetBrains.Kotlin.kotlinStdlib8)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Versions.JAVA_VERSION_STR
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = Versions.JAVA_VERSION_STR
}