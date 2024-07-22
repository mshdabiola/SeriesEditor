

import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureAndroidCompose
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("com.android.compose.screenshot")

            //  val extension = extensions.getByType<LibraryExtension>()
            extensions.configure<LibraryExtension> {
                extensions.configure<LibraryExtension> {
                    configureAndroidCompose(this)

                    experimentalProperties["android.experimental.enableScreenshotTest"] = true
                }

            }

           dependencies {
               add("screenshotTestImplementation",  project(":modules:designsystem"))
               add("screenshotTestImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
//               add("jvmTestImplementation",  project(":modules:testing"))
//               add("commonTestImplementation",  project(":modules:testing"))



           }
        }
    }

}