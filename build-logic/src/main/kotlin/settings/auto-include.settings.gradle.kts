package settings

fun includeWithParents(path: String, dir: File) {
    val segments = path.removePrefix(":").split(":")
    var currentPath = ""
    var currentDir = rootDir

    segments.forEach { segment ->
        currentPath += ":$segment"
        currentDir = File(currentDir, segment)

        if (findProject(currentPath) == null) {
            include(currentPath)
            project(currentPath).projectDir = currentDir.canonicalFile
        }
    }

    project(path).projectDir = dir.canonicalFile
}

fun scan(container: File) {
    fun walk(dir: File, path: String) {
        val hasBuild =
            File(dir, "build.gradle.kts").exists() ||
                    File(dir, "build.gradle").exists()

        if (hasBuild) {
            includeWithParents(path, dir)
        }

        dir.listFiles()
            ?.filter { it.isDirectory }
            ?.forEach { sub ->
                walk(sub, "$path:${sub.name}")
            }
    }

    walk(container, ":${container.name}")
}

listOf("module", "services")
    .map { file(it) }
    .filter { it.exists() }
    .forEach { scan(it) }
