plugins {
    id("web-flux-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter.security)
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("io.asyncer:r2dbc-mysql")
}