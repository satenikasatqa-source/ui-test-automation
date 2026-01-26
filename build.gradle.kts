plugins {
    java
    id("io.qameta.allure") version "2.11.2"
}

group = "com.demoqa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.27.0"

allure {
    report {
        version.set(allureVersion)
    }
    adapter {
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set(allureVersion)
            }
        }
    }
}

dependencies {
    testImplementation("com.codeborne:selenide:7.9.4")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.12")
}

tasks.test {
    useJUnitPlatform()

    systemProperty("allure.results.directory", "$buildDir/allure-results")
    systemProperties(System.getProperties().mapKeys { it.key.toString() })
}
