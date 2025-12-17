import org.gradle.kotlin.dsl.project

plugins {
    id("spring-boot-conventions")
    id("lombok-conventions")
    id("spring-boot-logging")
    id("spring-boot-monitoring")
    id("spring-boot-test")
}

dependencies {
    api(project(":module:code-crew-core"))
    api(project(":module:code-crew-logging"))
    api(project(":module:code-crew-monitoring"))
}