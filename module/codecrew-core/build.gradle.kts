plugins {
    id("spring-framework-conventions")
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.jackson.annotation)
    implementation(libs.spring.boot.starter)
}