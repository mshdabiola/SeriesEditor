@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
}

android {
    namespace = "com.mshdabiola.data"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))
                implementation(project(":modules:analytics"))

                implementation(libs.koin.core)
                //   implementation(libs.kermit.log)
                implementation(project(":modules:model"))
                implementation(project(":modules:database"))
                implementation(project(":modules:datastore"))
                implementation(project(":modules:network"))
                implementation(libs.kotlinx.coroutines.core)
                //implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.5.1")
                implementation(libs.paging.common)

                // alternatively - without Android dependencies for tests
                //testImplementation "androidx.paging:paging-common:$paging_version"

            }
        }

    }
}