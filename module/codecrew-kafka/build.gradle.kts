plugins {
    id("spring-boot-conventions")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")


dependencies {
    api(libs.findLibrary("spring-kafka").get())
}