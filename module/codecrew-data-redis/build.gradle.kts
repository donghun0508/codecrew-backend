plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.bundles.spring.boot.data.redis)
    implementation(project(":module:codecrew-core"))
    testFixturesImplementation("com.redis:testcontainers-redis")
}