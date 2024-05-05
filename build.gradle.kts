val plasmoVoiceVersion: String by rootProject

plugins {
    kotlin("jvm") version("1.6.10")
    id("com.github.johnrengelman.shadow") version("7.0.0")
    id("su.plo.voice.plugin") version("1.0.2-SNAPSHOT")
    `maven-publish`
}

group = "su.plo"
version = "1.0.9"

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

    compileOnly("com.github.lavalink-devs.lavaplayer-youtube-source:plugin:9b233d801e13eafdca7ba99c43a42dc56fa6840b")
    shadow("com.github.lavalink-devs.lavaplayer-youtube-source:plugin:9b233d801e13eafdca7ba99c43a42dc56fa6840b")

    compileOnly("dev.arbjerg:lavaplayer:20f37dff305238a382557cb98b6b714516c23a99-SNAPSHOT")
    shadow("dev.arbjerg:lavaplayer:20f37dff305238a382557cb98b6b714516c23a99-SNAPSHOT") {
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

        relocate("dev.lavalink", "su.plo.voice.lavaplayer.libs.dev.lavalink")
        relocate("com.grack", "su.plo.voice.lavaplayer.libs.com.grack")

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
        maven("https://repo.plasmoverse.com/releases") {
            name = "plasmoverseReleases"

            if (System.getenv("MAVEN_USERNAME") != null) {
                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }

            if (findProperty("plasmoverseReleasesUsername") != null) {
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}
