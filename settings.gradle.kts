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
        maven("https://jitpack.io")
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/TorangToml/libs.versions.toml"))
        }
    }
}

rootProject.name = "Main"        // 루트 프로젝트 정의
include(":app")     // 하위 프로젝트 1
include(":library") // 하위 프로젝트 2