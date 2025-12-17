rootProject.name = "codecrew"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("settings.auto-include")
    id("settings.catalogs")
}