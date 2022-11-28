@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "StarWarsTask"
include(
    ":app",
    ":lib-generic:network",
    ":lib-generic:ui",
    ":lib-specific:starwars-api",
    ":lib-specific:ui-theme",
)
