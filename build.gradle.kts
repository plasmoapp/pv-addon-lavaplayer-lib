val plasmoVoiceVersion: String by rootProject

plugins {
    kotlin("jvm") version("1.8.22")
    id("com.github.johnrengelman.shadow") version("7.0.0")
    id("su.plo.voice.plugin.entrypoints") version("1.0.2-SNAPSHOT")
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.plo.su")
    maven("https://repo.plasmoverse.com/snapshots")
    maven("https://repo.plasmoverse.com/releases")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly("su.plo.voice.api:server:$plasmoVoiceVersion")
    compileOnly("su.plo.voice.api:proxy:$plasmoVoiceVersion")

    compileOnly("dev.arbjerg:lavaplayer:1.5.2")
    shadow("dev.arbjerg:lavaplayer:1.5.2") {
        exclude("org.slf4j")
    }
}

tasks {
    java {
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(16))
    }

    shadowJar {
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
        relocate("org.mozilla", "su.plo.voice.lavaplayer.libs.org.mozilla")
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
