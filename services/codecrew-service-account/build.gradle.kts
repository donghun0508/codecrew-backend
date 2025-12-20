plugins {
    id("application-conventions")
}

dependencies {
    implementation(domainAccountLibs.bundles.google.auth)
    implementation(domainAccountLibs.bundles.json.web.token)
}