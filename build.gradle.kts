plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.digital-hustle"
version = "0.0.1-SNAPSHOT"
description = "Spring boot dependency for convenient exception handling"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

// versions
val lombokVersion = "1.18.38"
val springBootStarterVersion = "4.0.3"
val jakartaPersistenceVersion = "3.2.0"
val junitVersion = "6.0.1"

dependencies {
    // starter
    implementation("org.springframework.boot:spring-boot-starter-webmvc:$springBootStarterVersion")

    // lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // any
    implementation("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceVersion")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test:$springBootStarterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVersion")
}

// plugins
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

// tasks
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}