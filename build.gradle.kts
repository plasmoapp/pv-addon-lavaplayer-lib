val plasmoVoiceVersion: String by rootProject

plugins {
    kotlin("jvm") version(libs.versions.kotlin.get())
    alias(libs.plugins.shadow)
    alias(libs.plugins.pv.entrypoints)
    alias(libs.plugins.pv.java.templates)
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.plasmoverse.com/snapshots")
    maven("https://repo.plasmoverse.com/releases")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.lavalink.dev/snapshots")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly(libs.pv.server)
    compileOnly(libs.pv.proxy)

    compileOnly(libs.lavaplayer)
    shadow(libs.lavaplayer) {
        exclude("org.slf4j")
    }
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(16)) // lavaplayer supports only java 16+
        withSourcesJar()
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
        if (properties.containsKey("snapshot")) {
            maven("https://repo.plasmoverse.com/snapshots") {
                name = "PlasmoVerseSnapshots"

                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }
        } else {
            maven("https://repo.plasmoverse.com/releases") {
                name = "PlasmoVerseReleases"

                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }
        }
    }
}
