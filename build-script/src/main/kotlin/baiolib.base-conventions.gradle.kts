plugins {
    `java-library`
}

group = "com.baioretto"
version = "1.6.0-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenLocal()

        mavenCentral()

        maven("https://repo.codemc.org/repository/nms-local/") // codemc local

        maven("https://repo.codemc.org/repository/maven-public/") // codemc public

        maven("https://repo.mattstudios.me/artifactory/public/") // mattstudios

        maven("https://repo.dmulloy2.net/repository/public/") // dmulloy2

        maven("https://repo.mattstudios.me/artifactory/public/") // mattstudios
    }

    dependencies {
        compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT") // maven local: spigot-api 1.18.2

        val `adventure-api-version` = "4.11.0"
        implementation("net.kyori:adventure-api:${`adventure-api-version`}")  // maven central: adventure-api 4.11.0
        implementation("net.kyori:adventure-text-serializer-legacy:${`adventure-api-version`}")  // maven central: adventure-text-serializer-legacy 4.11.0

        api("de.tr7zw:item-nbt-api-plugin:2.10.0") // codemc public: nbt api

        api("com.google.code.gson:gson:2.9.0") // maven central: gson

        api("dev.triumphteam:triumph-gui:3.1.3") // mattstudios : triumph-gui

        api("org.apache.commons:commons-lang3:3.12.0") // maven central: commons lang3

        api("org.reflections:reflections:0.1-SNAPSHOT") // github package: reflection util
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0") // maven central
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0") // maven central
    }

    tasks.test {
        useJUnitPlatform()
    }
}