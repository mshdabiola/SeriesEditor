/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
    id("mshdabiola.android.library.jacoco")

    alias(libs.plugins.roborazzi)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.designsystem"
}

dependencies {
    // lintPublish(projects.lint)


    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.coil.kt.compose)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.accompanist.testharness)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(project(":modules:testing"))

    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(project(":modules:testing"))
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")

}
kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(libs.kotlinx.collection.immutable)
               implementation(project(":modules:model"))
                api(libs.androidx.compose.material3.windowSizeClass)
                api(libs.navigation.compose)
                api(libs.paging.compose.common)

                api(libs.koin.compose)
                api(libs.koin.composeVM)
                api(libs.lifecycle.viewmodel.compose)





            }
        }
        val androidMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.androidx.lifecycle.runtimeCompose)
                api(libs.androidx.lifecycle.viewModelCompose)

            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.kotlinx.coroutines.swing)
            }
        }


    }
}
task("testClasses")
