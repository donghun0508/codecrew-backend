plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter.data.redis)
    testFixturesImplementation("com.redis:testcontainers-redis")
}