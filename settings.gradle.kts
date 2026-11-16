pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases")
    }
}


if (JavaVersion.current() < JavaVersion.VERSION_25) throw IllegalStateException("Please use Java 25+!")

rootProject.name = "durability777"
