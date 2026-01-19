plugins {
    id("core-conventions")
}

dependencies {
    implementation(domainApigatewayLibs.bundles.netty)
    implementation(domainApigatewayLibs.spring.cloud.gateway.webflux)

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
}