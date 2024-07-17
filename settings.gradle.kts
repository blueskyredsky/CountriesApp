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
    }
}
rootProject.name = "CountriesApp"
include(":app:mobile")
include(":app:wear")
include(":benchmark")
include(":feature:home")
include(":feature:details")
include(":feature:search")
include(":feature:widget")
include(":core:threading")
include(":core:networking")
include(":core:common")
include(":core:logging")
include(":core:testing:unit")
include(":core:testing:ui")
include(":core:systemdesign")
