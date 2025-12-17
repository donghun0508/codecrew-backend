package settings

dependencyResolutionManagement {
    versionCatalogs {

        fun catalogName(file: File): String =
            file.name
                .removeSuffix(".versions.toml")
                .removeSuffix(".version.toml")
                .split(".", "-", "_")
                .joinToString("") { it.replaceFirstChar(Char::uppercase) }
                .replaceFirstChar(Char::lowercase)

        fun loadCatalogs(dir: File) {
            dir.listFiles()
                ?.filter { it.isFile && it.name.endsWith(".toml") }
                ?.forEach { file ->
                    create(catalogName(file)) {
                        from(files(file))
                    }
                }
        }

        loadCatalogs(file("gradle/version"))
        file("gradle/version/domain")
            .takeIf { it.exists() }
            ?.let(::loadCatalogs)
    }
}
