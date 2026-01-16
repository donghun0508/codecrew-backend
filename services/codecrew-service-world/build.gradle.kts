plugins {
    id("web-flux-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter.security)
    runtimeOnly("io.asyncer:r2dbc-mysql")
}