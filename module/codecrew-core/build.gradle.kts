plugins {
    id("spring-framework-conventions")
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.jackson.annotation)
    implementation(libs.spring.boot.starter)
    implementation(libs.io.github.classgraph)
    implementation(libs.spring.framework.web)
    implementation(libs.spring.boot.starter.validation)
    runtimeOnly(libs.netty.resolver.dns.macos) {
        artifact {
            classifier = "osx-aarch_64"
        }
    }
}