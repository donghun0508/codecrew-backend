plugins {
    id("core-conventions")
    id("spring-boot-web-flux")
    id("spring-boot-data-redis")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    api(project(":module:codecrew-data-redis"))
    api(project(":module:codecrew-data-r2dbc"))
}
