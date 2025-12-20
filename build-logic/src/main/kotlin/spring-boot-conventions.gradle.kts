plugins {
    id("java-conventions")
    id("lombok-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    api(libs.findLibrary("spring-boot-starter-json").get())
    api(libs.findLibrary("jackson-datatype-jsr310").get())
}

dependencyManagement {
    imports {
        mavenBom(
            "org.springframework.cloud:spring-cloud-dependencies:" +
                    libs.findVersion("springCloudDependenciesVersion").get().requiredVersion
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
