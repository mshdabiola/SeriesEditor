@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.datastore"
    //proguard here
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))
                implementation(libs.model)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                api(libs.androidx.dataStore.core)
                api(libs.androidx.datastore.core.okio)

            }
        }
//
//        val commonTest by getting {
//            dependencies {
//            }
//        }
//
//        val androidMain by getting {
//            dependencies {
//            }
//        }
//
//
//
//        val desktopMain by getting {
//            dependencies {
//
//            }
//        }
//
//        val desktopTest by getting

//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}
//
//dependencies {
//
//    testImplementation(project(":core:testing"))
//    implementation(libs.paging.runtime)
//    implementation(libs.paging.common)
//}
