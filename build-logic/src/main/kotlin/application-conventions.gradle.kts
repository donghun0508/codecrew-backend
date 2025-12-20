import org.gradle.kotlin.dsl.project

plugins {
    id("core-conventions")
    id("spring-boot-web-mvc")
    id("spring-boot-data-jpa")
    id("spring-boot-data-redis")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    api(libs.findBundle("mysql-datasource").get())

    api(project(":module:codecrew-starter-web"))
    api(project(":module:codecrew-data-jpa"))
    api(project(":module:codecrew-data-redis"))
}
