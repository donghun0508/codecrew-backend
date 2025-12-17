import org.gradle.api.Project.DEFAULT_VERSION

fun getGitHash(): String =
    runCatching {
        providers.exec {
            commandLine("git", "rev-parse", "--short", "HEAD")
        }.standardOutput.asText.get().trim()
    }.getOrElse { "init" }


allprojects {
    group = findProperty("projectGroup") as String
    // version = if (version == DEFAULT_VERSION) getGitHash() else version
}