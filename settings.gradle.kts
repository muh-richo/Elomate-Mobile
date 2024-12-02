pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
        jcenter()
    }
}


rootProject.name = "Elomate"
include(":app")
 