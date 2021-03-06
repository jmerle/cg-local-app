import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.jaspervanmerle.cglocal"
version = "1.2.0"

application {
    mainClass.set("$group.MainKt")
    mainClassName = mainClass.get()
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("io.github.microutils:kotlin-logging:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.mihnita:color-loggers:1.0.5")
    implementation("org.java-websocket:Java-WebSocket:1.5.1")
    implementation("org.json:json:20200518")
    implementation("name.mitterdorfer.perlock:perlock-core:0.3.1")
    implementation("org.koin:koin-core:2.1.6")
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
