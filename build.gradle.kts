plugins {
    java
    jacoco
}

group = "net.kirinboy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.mockito:mockito-junit-jupiter:5.+")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}