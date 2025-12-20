plugins {
    id("spring-framework-conventions")
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.jackson.annotation)
    implementation(libs.spring.boot.starter)
    implementation(libs.io.github.classgraph)
    runtimeOnly(libs.netty.resolver.dns.macos) {
        artifact {
            classifier = "osx-aarch_64"
        }
    }
}