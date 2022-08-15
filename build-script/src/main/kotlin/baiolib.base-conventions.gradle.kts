plugins {
    `java-library`
}

group = "com.baioretto"
version = "1.6.0-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "baiolib.library")

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0") // maven central
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0") // maven central
    }

    tasks.test {
        useJUnitPlatform()
    }
}