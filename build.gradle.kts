import org.gradle.internal.deployment.RunApplication
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.2.71"
}

group = "com.jaspervanmerle.cglocal"
version = "1.1.0"

application {
    mainClassName = "$group.CGLocal"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("io.github.microutils:kotlin-logging:1.6.10")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("com.mihnita:color-loggers:1.0.5")
    compile("no.tornado:tornadofx:1.7.17")
    compile("de.jensd:fontawesomefx:8.9")
    compile("org.java-websocket:Java-WebSocket:1.3.9")
    compile("org.json:json:20180813")
    compile("name.mitterdorfer.perlock:perlock-core:0.3.1")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Jar> {
        doFirst {
            manifest {
                attributes(mapOf(
                    "Implementation-Title" to "CGLocal",
                    "Implementation-Version" to version,
                    "Main-Class" to application.mainClassName
                ))
            }

            from(configurations.compile.map { if (it.isDirectory) it else zipTree(it) })
        }
    }
}
