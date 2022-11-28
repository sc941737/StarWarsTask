import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

sourceSets["main"].java.srcDir("src/main/kotlin")