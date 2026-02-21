plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.digital-hustle"
version = "0.0.1-SNAPSHOT"
description = "Spring boot dependency for convenient exception handling"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
    withJavadocJar()
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

val repoOwnerName = "Digital-Hustle"
val repoName = "exception-starter"

dependencies {
    // starter
    implementation("org.springframework.boot:spring-boot-starter-webmvc:$springBootStarterVersion")

    // processor
    implementation("org.springframework.boot:spring-boot-configuration-processor:$springBootStarterVersion")

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
            pom {
                name.set("Exception Handling Spring Boot Starter")
                description.set(project.description)
                url.set("https://github.com/$repoOwnerName/$repoName")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("DanyaChetvyrtov")
                        name.set("dasemenov")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/$repoOwnerName/$repoName.git")
                    developerConnection.set("scm:git:ssh://github.com/$repoOwnerName/$repoName.git")
                    url.set("https://github.com/$repoOwnerName/$repoName")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/$repoOwnerName/$repoName")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key")?.toString() ?: System.getenv("TOKEN")
            }
        }
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