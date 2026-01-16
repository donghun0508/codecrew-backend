plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.bundles.spring.boot.data.jpa)
    implementation(project(":module:codecrew-core"))
}