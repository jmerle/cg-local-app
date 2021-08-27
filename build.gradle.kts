import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.jaspervanmerle.cglocal"
version = "1.2.0"

application {
    mainClass.set("$group.MainKt")
    mainClassName = mainClass.get()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("com.mihnita:color-loggers:1.0.5")
    implementation("org.java-websocket:Java-WebSocket:1.5.2")
    implementation("org.json:json:20210307")
    implementation("name.mitterdorfer.perlock:perlock-core:0.3.1")
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("com.github.jiconfont:jiconfont-swing:1.0.1")
    implementation("com.github.jiconfont:jiconfont-font_awesome:4.7.0.1")
    implementation("com.miglayout:miglayout:3.7.4")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<ShadowJar> {
        archiveFileName.set("cg-local-app-${project.version}.jar")
    }
}
