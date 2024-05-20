@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

}

android {
    namespace = "com.mshdabiola.mvvn"
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(compose.runtime)
                implementation(compose.ui)
                // api(libs.lifecycle.viewmodel.compose)


            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {


                api(libs.androidx.core.ktx)



                implementation(libs.androidx.lifecycle.runtimeCompose)
                api(libs.androidx.lifecycle.viewModelCompose)
            }
        }


        val jvmMain by getting {
            dependencies {

                implementation(libs.kotlinx.coroutines.swing)
            }
        }


//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}