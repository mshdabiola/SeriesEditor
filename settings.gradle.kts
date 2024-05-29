pluginManagement {
    repositories {
        includeBuild("build-logic")
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

//val project = File(rootDir, "local.properties").inputStream().use {
//    java.util.Properties().apply { load(it) }
//}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
      //  mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/mshdabiola/series")
            credentials {
                username = "41789315"//project.getProperty("gpr.user")  ?: System.getenv("USERNAME")
                password = "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"//project.getProperty("gpr.key") ?: System.getenv("TOKEN")
            }
        }
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}
rootProject.name = "SeriesEditor"
//include(":app")
//include(":app:baselineprofile")
include(":modules:database")
include(":modules:designsystem")
include(":modules:model")
include(":modules:network")
include(":modules:data")
include(":modules:domain")
include(":modules:testing")
include(":modules:ui")
//include(":modules:mvvn")
include(":modules:analytics")
include(":modules:datastore")

//include(":modules:app")
//include(":desktop")
//include(":modules:setting")

include(":benchmarks")


include(":seriesEditorApp")
//include(":shared")


include(":features:main")
include(":features:detail")
include(":features:setting")


