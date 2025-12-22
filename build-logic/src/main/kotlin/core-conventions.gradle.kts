import org.gradle.kotlin.dsl.project

plugins {
    id("spring-boot-conventions")
    id("lombok-conventions")
    id("spring-boot-logging")
    // id("spring-boot-monitoring")
    id("spring-boot-test")
}

dependencies {
    api(project(":module:codecrew-core"))
    api(project(":module:codecrew-logging"))
    // api(project(":module:codecrew-monitoring"))
}