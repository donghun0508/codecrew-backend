import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.java
import org.gradle.kotlin.dsl.`java-library`
import org.gradle.kotlin.dsl.repositories

plugins {
    java
    `java-library`
    `java-test-fixtures`
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(
            JavaLanguageVersion.of(
                libs.findVersion("java").get().toString()
            )
        )
    }
}

tasks.compileJava {
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all"
        )
    )
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            )
        )
    }
}
