import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    kotlin("jvm") version "1.3.72"
}

group = "com.jaspervanmerle.cglocal"
version = "1.1.1"

application {
    mainClassName = "$group.CGLocal"
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
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
