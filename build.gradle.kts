val plasmoVoiceVersion: String by rootProject

plugins {
    kotlin("jvm") version(libs.versions.kotlin.get())
    alias(libs.plugins.shadow)
    alias(libs.plugins.pv.entrypoints)
    alias(libs.plugins.pv.java.templates)
    `maven-publish`
}

if (properties.containsKey("snapshot")) {
    version = "$version-SNAPSHOT"
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.plasmoverse.com/snapshots")
    maven("https://repo.plasmoverse.com/releases")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.lavalink.dev/snapshots")
    maven("https://maven.lavalink.dev/releases")

    // https://github.com/lavalink-devs/youtube-source/pull/123
    maven("https://maven.kikkia.dev/snapshots")

//    maven("https://maven.topi.wtf/releases")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly(libs.pv.server)
    compileOnly(libs.pv.proxy)

    shadow(libs.lavaplayer.youtube)

//    shadow("com.github.topi314.lavasrc:lavasrc:4.3.0") {
//        exclude("org.jetbrains.kotlin")
//        exclude("org.jetbrains.kotlinx")
//    }
//    shadow("com.github.topi314.lavasrc:lavasrc-protocol:4.3.0") {
//        exclude("org.jetbrains.kotlin")
//        exclude("org.jetbrains.kotlinx")
//    }

    shadow(libs.lavaplayer) {
        exclude("org.slf4j")
    }
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(11)) // lavaplayer supports only java 11
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
        relocate("org.intellij", "su.plo.voice.lavaplayer.libs.org.intellij")
        relocate("org.jetbrains", "su.plo.voice.lavaplayer.libs.org.jetbrains")
//        relocate("org.mozilla", "su.plo.voice.lavaplayer.libs.org.mozilla")

        relocate("dev.lavalink", "su.plo.voice.lavaplayer.libs.dev.lavalink")
        relocate("com.grack", "su.plo.voice.lavaplayer.libs.com.grack")
        relocate("com.github.topi314", "su.plo.voice.lavaplayer.libs.com.github.topi314")
        relocate("com.auth0", "su.plo.voice.lavaplayer.libs.com.auth0")
        relocate("dev.schlaubi", "su.plo.voice.lavaplayer.libs.dev.schlaubi")

        exclude("lavalink-plugins/**")

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
