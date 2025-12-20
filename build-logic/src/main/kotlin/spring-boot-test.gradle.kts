plugins {
    id("spring-boot-conventions")
    id("java-test-fixtures")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    testImplementation(libs.findBundle("spring-boot-test").get())
}