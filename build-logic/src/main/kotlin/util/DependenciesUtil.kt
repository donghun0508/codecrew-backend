package util

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

fun Project.implBundle(name: String) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies.add("api", libs.findBundle(name).get())
}

fun Project.testImplBundle(name: String) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies.add("testImplementation", libs.findBundle(name).get())
}

fun Project.errorProne(name: String) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies.add("errorprone", libs.findLibrary(name).get())
}

fun Project.springFrameworkBom() =
    extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findLibrary("spring-framework-bom")
        .get()

fun Project.springCloudBom() =
    extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findLibrary("spring-cloud-bom")
        .get()