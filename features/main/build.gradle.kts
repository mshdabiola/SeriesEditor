/*
 *abiola 2022
 */

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.feature")
}

android {
    namespace = "com.mshdabiola.main"
}
kotlin {
    sourceSets {
        val jvmMain by getting{
            dependencies{
                implementation(libs.calf.filepicker)

            }
        }
        val commonMain by getting {
            dependencies {
                api(compose.components.resources)
            }
        }


    }
}
task("testClasses")
