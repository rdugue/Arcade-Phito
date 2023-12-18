import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            val properties = Properties()
            properties.load(File(rootProject.projectDir, "local.properties").inputStream())

            url = uri("https://maven.pkg.github.com/rdugue/Phito-Arch")
            credentials {
                username = properties.getProperty("GITHUB_ACTOR") ?: System.getenv("GITHUB_ACTOR")
                password = properties.getProperty("GITHUB_TOKEN") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
rootProject.name = "ArcadePhito"
include(":app")