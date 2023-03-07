plugins {
    kotlin("jvm") version("1.6.10")
    kotlin("kapt") version ("1.6.10")
    id("com.github.johnrengelman.shadow") version("7.0.0")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
    `maven-publish`
}

group = "su.plo"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.plo.su")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("su.plo.voice.api:server:2.0.0+ALPHA")
    implementation("com.github.walkyst:lavaplayer-fork:1.4.0")
    shadow("com.github.walkyst:lavaplayer-fork:1.4.0") {
        exclude("org.slf4j")
    }

    kapt("su.plo.voice.api:server:2.0.0+ALPHA")
}

tasks {
    java {
        withSourcesJar()
    }

    shadowJar {
        configurations = listOf(project.configurations.shadow.get())

        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveAppendix.set("")

        relocate("org.apache", "su.plo.voice.lavaplayer.org.apache")
        relocate("org.jsoup", "su.plo.voice.lavaplayer.org.jsoup")
        relocate("com.fasterxml", "su.plo.voice.lavaplayer.com.fasterxml")
        relocate("com.fasterxml", "su.plo.voice.lavaplayer.com.fasterxml")
        relocate("net.iharder", "su.plo.voice.lavaplayer.net.iharder")
        relocate("ibxm", "su.plo.voice.lavaplayer.ibxm")
        relocate("net.sourceforge", "su.plo.voice.lavaplayer.net.sourceforge")
        relocate("org.json", "su.plo.voice.lavaplayer.org.json")
        relocate("org.mozilla", "su.plo.voice.lavaplayer.org.mozilla")
    }

    build {
        dependsOn(shadowJar)
    }
}

configure<PublishingExtension> {
    publications.create<MavenPublication>(project.name) {
        from(components["java"])
    }

    repositories {
        val mavenUser = project.findProperty("maven_user")
        val mavenPassword = project.findProperty("maven_password")

        if (mavenUser != null && mavenPassword != null) {
            maven("https://repo.plo.su/public/") {
                credentials {
                    username = mavenUser.toString()
                    password = mavenPassword.toString()
                }
            }
        }
    }
}

bukkit {
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "su.plo.voice.lavaplayer.BukkitEntryPoint"
    apiVersion = "1.16"
    authors = listOf("Apehum")
    depend = listOf("PlasmoVoice")
}
