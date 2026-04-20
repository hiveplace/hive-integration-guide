pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
//        mavenLocal()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://identy.jfrog.io/identy/identy-face-release")
            credentials {
                username = System.getenv("JFROG_USERNAME")
                password = System.getenv("JFROG_TOKEN")
            }
        }

        maven {
            url = uri("https://repo.incognia.com/android/")
            content { includeGroup("com.incognia") }
        }
//        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/hiveplace/hive-sdk-validation-face-android")
            credentials {
                username = settings.providers.gradleProperty("HIVE_SDK_USERNAME").getOrElse("")
                password = settings.providers.gradleProperty("HIVE_SDK_PASSWORD").getOrElse("")
            }
        }
    }
}

rootProject.name = "Hive Integration Guide"
include(":app")
