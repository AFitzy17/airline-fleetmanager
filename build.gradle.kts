plugins {
    kotlin("jvm") version "2.2.20"
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    application
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.17")
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    // https://mvnrepository.com/artifact/org.codehaus.jettison/jettison
    implementation("org.codehaus.jettison:jettison:1.4.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
