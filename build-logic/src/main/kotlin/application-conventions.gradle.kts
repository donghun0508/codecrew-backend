import org.gradle.kotlin.dsl.project

plugins {
    id("core-conventions")
    id("spring-boot-web-mvc")
    id("spring-boot-jpa")
}

dependencies {
    api(project(":module:code-crew-web"))
    api(project(":module:code-crew-jpa"))
}
