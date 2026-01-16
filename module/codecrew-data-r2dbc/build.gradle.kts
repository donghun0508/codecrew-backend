plugins {
    id("spring-boot-web-flux")
}

dependencies {
    api(libs.spring.boot.starter.data.r2dbc)
    implementation(project(":module:codecrew-core"))
}