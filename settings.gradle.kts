

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

        maven{
            url = uri("http://maven.usehover.com/releases")
            isAllowInsecureProtocol  = true
        }


    }
}



rootProject.name = "Rova Pay"
include(":app")
