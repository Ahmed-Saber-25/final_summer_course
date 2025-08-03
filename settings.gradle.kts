pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.application") version "8.11.1"
        id("org.jetbrains.kotlin.android") version "2.2.0"
        id("androidx.navigation.safeargs.kotlin") version "2.9.3"
        id("org.jetbrains.kotlin.plugin.parcelize") version "2.2.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "recipe_app"
include(":app")
