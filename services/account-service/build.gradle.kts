plugins {
    id("application-conventions")
}

dependencies {
    implementation(domainAccountLibs.bundles.google.auth)
    implementation(domainAccountLibs.bundles.json.web.token)
    implementation(libs.bundles.mysql.datasource)
}