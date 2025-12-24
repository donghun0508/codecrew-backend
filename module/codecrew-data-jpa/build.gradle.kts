
plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(project(":module:codecrew-core"))
}