plugins {
    id("core-conventions")
}

dependencies {
    implementation(domainApigatewayLibs.bundles.netty)
    implementation(domainApigatewayLibs.spring.cloud.gateway.webflux)
}