plugins {
    id("application-conventions")
}

dependencies {
    implementation(domainMemberLibs.keycloak.admin.client)
}