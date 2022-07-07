import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "de.leonheuer.skycave"
version = "3.0.2"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.github.heuerleon:mcguiapi:v1.3.3")
    compileOnly("org.mongodb:mongodb-driver-sync:4.6.0")
}

tasks {
    test {
        useJUnit()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}