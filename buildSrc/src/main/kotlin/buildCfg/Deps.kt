object Deps {

    object AndroidX {
        // Sources:
        // - repo: https://android.googlesource.com/platform/frameworks/support/+/HEAD/
        // - code search: https://cs.android.com/androidx
        // Versions: https://developer.android.com/jetpack/androidx/versions
        const val coreKtx = "androidx.core:core-ktx:1.9.0"

        object Activity {
            // INFO: https://developer.android.com/jetpack/androidx/releases/activity
            // MVN:  https://maven.google.com/web/index.html#androidx.activity
            private const val version = "1.6.1"
            const val activity = "androidx.activity:activity:$version"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
            const val activityCompose = "androidx.activity:activity-compose:$version"
        }

        object Compose {
            // Versions: https://developer.android.google.cn/jetpack/androidx/releases/compose?hl=en#versions
            // Sources: https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-compose-release/
            const val version = "1.3.1"

            object Animation {
                // https://developer.android.com/jetpack/androidx/releases/compose-animation
                // https://maven.google.com/web/index.html#androidx.compose.animation
                const val animation = "androidx.compose.animation:animation:$version"
            }
            object Compiler {
                // CHANGELOG: https://developer.android.com/jetpack/androidx/releases/compose-compiler
                // MAVEN:     https://maven.google.com/web/index.html#androidx.compose.compiler
                // Compatibility map:
                // https://developer.android.com/jetpack/androidx/releases/compose-kotlin#pre-release_kotlin_compatibility
                // to support newer Kotlin version use develop builds:
                // https://androidx.dev/storage/compose-compiler/repository
                // more about versioning of Compose Compiler:
                // https://android-developers.googleblog.com/2022/06/independent-versioning-of-Jetpack-Compose-libraries.html
                const val compiler = "androidx.compose.compiler:compiler:$version"
                // NOTE: Compose Compiler's version can be set explicitly
                //       with android.composeOptions.kotlinCompilerExtensionVersion.
            }
            object Foundation {
                // https://developer.android.com/jetpack/androidx/releases/compose-foundation
                // https://maven.google.com/web/index.html#androidx.compose.foundation
                const val foundation = "androidx.compose.foundation:foundation:$version"
            }
            object Material {
                // https://developer.android.com/jetpack/androidx/releases/compose-material
                // https://maven.google.com/web/index.html#androidx.compose.material
                const val material = "androidx.compose.material:material:$version"
                const val materialIconsCore = "androidx.compose.material:material-icons-core:$version"
                const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            }
            object Ui {
                // https://developer.android.com/jetpack/androidx/releases/compose-ui
                // https://maven.google.com/web/index.html#androidx.compose.ui
                const val ui = "androidx.compose.ui:ui:$version"
                // Tooling support (Previews, etc.)
                const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
                const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
                const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
                const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
            }
        }

        object Lifecycle {
            // INFO: https://developer.android.com/jetpack/androidx/releases/lifecycle
            // https://maven.google.com/web/index.html#androidx.lifecycle
            // https://mvnrepository.com/artifact/androidx.lifecycle
            // Sources: https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-main/lifecycle/
            private const val version = "2.5.1"
            const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:$version"
            const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$version"
            // ViewModel
            const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val lifecycleViewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }
    } // End of AndroidX

    object JetBrains {
        object Kotlin {
            // CHANGELOG: https://kotlinlang.org/docs/releases.html#release-details
            //            https://github.com/JetBrains/kotlin/releases
            const val version = Versions.KOTLIN
            const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
            const val kotlinStdlib8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
            const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
            const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        }

        object KotlinX {


            object Collections {
                private const val version = "0.3.2"
                const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$version"
                const val kotlinxCollectionsImmutableJvm =
                    "org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:$version"
            }

            object Coroutines {
                // CHANGELOG: https://github.com/Kotlin/kotlinx.coroutines/blob/master/CHANGES.md
                // MAVEN:     https://mvnrepository.com/artifact/org.jetbrains.kotlinx/
                //            https://search.maven.org/search?q=g:org.jetbrains.kotlinx%20AND%20a:kotlinx-coroutines*
                private const val version = "1.6.4"
                const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
                const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            }

            object Serialization {
                const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
                const val classPath = "org.jetbrains.kotlinx:kotlinx-serialization:1.5.21"
            }
        }
    } // End of JetBrains

    object Test {
        // INFO: https://developer.android.com/jetpack/androidx/releases/test
        // MVN:  https://maven.google.com/web/index.html#androidx.test
        private const val version = "1.5.1"
        // Core library
        const val core = "androidx.test:core:$version"

        object Espresso {
            private const val version = "3.5.0"
            const val espressoCore = "androidx.test.espresso:espresso-core:$version"
        }

        object Ext {
            // MVN:  https://maven.google.com/web/index.html#androidx.test.ext
            private const val junitVersion = "1.1.4"
            const val junitKtx = "androidx.test.ext:junit-ktx:$junitVersion"
            private const val truthVersion = "1.5.0"
            const val truth = "androidx.test.ext:truth:$truthVersion"
        }

        object JUnit {
            // https://search.maven.org/artifact/junit/junit
            const val junit4 = "junit:junit:4.13.2"
        }
    } // End of Test

    object Koin {
        private const val version = "3.3.0"
        const val android = "io.insert-koin:koin-android:$version"
        const val androidCompose = "io.insert-koin:koin-androidx-compose:$version"
        const val junit4 = "io.insert-koin:koin-test-junit4:$version"
    }

    object Ktor {
        private const val version = "2.1.3"
        const val ktorClientAndroid = "io.ktor:ktor-client-android:$version"
        const val ktorClientCore = "io.ktor:ktor-client-core:$version"
        const val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val ktorClientLogging = "io.ktor:ktor-client-logging:$version"
        const val ktorClientResources = "io.ktor:ktor-client-resources:$version"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization:$version"
        const val ktorClientSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    object Raamcosta {
        object ComposeDestinations {
            const val version = "1.7.27-beta"
            const val core = "io.github.raamcosta.compose-destinations:core:$version"
            const val ksp = "io.github.raamcosta.compose-destinations:ksp:$version"
        }
    }
} // End of Deps