val plasmoVoiceVersion: String by rootProject

plugins {
    kotlin("jvm") version("1.6.10")
    id("com.github.johnrengelman.shadow") version("7.0.0")
    id("su.plo.voice.plugin") version("1.0.2-SNAPSHOT")
    `maven-publish`
}

group = "su.plo"
version = "1.0.7"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.plo.su")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.lavalink.dev/snapshots")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly("su.plo.voice.api:server:$plasmoVoiceVersion")
    compileOnly("su.plo.voice.api:proxy:$plasmoVoiceVersion")

    compileOnly("dev.arbjerg:lavaplayer:495ea87beef35b161fc14138ab3523ecb97ba684-SNAPSHOT")
    shadow("dev.arbjerg:lavaplayer:495ea87beef35b161fc14138ab3523ecb97ba684-SNAPSHOT") {
        exclude("org.slf4j")
    }
}

tasks {
    java {
        withSourcesJar()
    }

    shadowJar {
        dependsOn(reobf)

        configurations = listOf(project.configurations.shadow.get())
        mergeServiceFiles()

        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveAppendix.set("")

        relocate("org.apache", "su.plo.voice.lavaplayer.libs.org.apache")
        relocate("org.jsoup", "su.plo.voice.lavaplayer.libs.org.jsoup")
        relocate("com.fasterxml", "su.plo.voice.lavaplayer.libs.com.fasterxml")
        relocate("com.fasterxml", "su.plo.voice.lavaplayer.libs.com.fasterxml")
        relocate("net.iharder", "su.plo.voice.lavaplayer.libs.net.iharder")
        relocate("ibxm", "su.plo.voice.lavaplayer.libs.ibxm")
        relocate("net.sourceforge", "su.plo.voice.lavaplayer.libs.net.sourceforge")
        relocate("org.json", "su.plo.voice.lavaplayer.libs.org.json")
//        relocate("org.mozilla", "su.plo.voice.lavaplayer.libs.org.mozilla")
        relocate("com.sedmelluq", "su.plo.voice.lavaplayer.libs.com.sedmelluq") {
            exclude("com/sedmelluq/discord/lavaplayer/natives/**")
        }
        relocate("mozilla", "su.plo.voice.lavaplayer.libs.mozilla")
        relocate("certificates", "su.plo.voice.lavaplayer.libs.certificates")
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
