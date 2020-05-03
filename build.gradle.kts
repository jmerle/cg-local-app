import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.jaspervanmerle.cglocal"
version = "1.1.1"

application {
    mainClassName = "$group.MainKt"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.microutils:kotlin-logging:1.6.10")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.mihnita:color-loggers:1.0.5")
    implementation("org.java-websocket:Java-WebSocket:1.4.1")
    implementation("org.json:json:20190722")
    implementation("name.mitterdorfer.perlock:perlock-core:0.3.1")
    implementation("org.koin:koin-core:2.1.5")
    implementation("com.github.jiconfont:jiconfont-swing:1.0.0")
    implementation("com.github.jiconfont:jiconfont-font_awesome:4.7.0.1")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<ShadowJar> {
        archiveFileName.set("cg-local-app.jar")
    }
}
