@file:Suppress("VulnerableLibrariesLocal")

plugins {
    idea
    `maven-publish`
    `java-library`
    id("io.freefair.lombok") version "6.5.0.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.baioretto.specialsource") version "1.0.0"
}

group = "com.baioretto"
version = "1.5.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "codemc local"
        url = uri("https://repo.codemc.org/repository/nms-local/")
    }
    maven {
        name = "codemc public"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        name = "mattstudios"
        url = uri("https://repo.mattstudios.me/artifactory/public/")
    }
    maven {
        name = "dmulloy2"
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        name = "paper"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

val minecraftVersion = "1.18.2-R0.1-SNAPSHOT"

dependencies {
    compileOnly("org.spigotmc:spigot:${minecraftVersion}:remapped-mojang") // maven local
}

dependencies {
    // nbt api
    api("de.tr7zw:item-nbt-api-plugin:2.10.0") // codemc public

    // gson
    api("com.google.code.gson:gson:2.9.0") // maven central

    // command util
    api("me.mattstudios.utils:matt-framework:1.4.6") // maven central

    // gui util
    api("dev.triumphteam:triumph-gui:3.1.3") // mattstudios

    // commons lang3
    api("org.apache.commons:commons-lang3:3.12.0") // maven central
}

dependencies {
    // guava
    testRuntimeOnly("com.google.guava:guava:31.1-jre") // maven central

    // gui util
    testRuntimeOnly("dev.triumphteam:triumph-gui:3.1.3") // mattstudios

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

dependencies {
    // reflection util
    api("org.reflections:reflections:0.1-SNAPSHOT") // github package
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    minimize()
    archiveBaseName.set(project.name)
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
    archiveExtension.set("jar")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType(JavaCompile::class).configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = LinkedHashMap<String, Any>()
    props["version"] = version
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

sourceSets {
    main {
        resources.srcDir("src/main/resource/")
    }
}

val mcversion = "1.18.2-R0.1-SNAPSHOT"
val normalJarName = "${project.name}-${version}.jar"
val normalJarPath = File(project.buildDir, "libs/${normalJarName}")
val obfuscatedJarName = "${project.name}-${version}-obf.jar"
val obfuscatedJarPath = File(project.buildDir, "libs/${obfuscatedJarName}")
val shadowJar by tasks.existing(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class)

val mmtmo = task("mmtmo", com.baioretto.specialsource.task.MojangMappingToMojangObfuscated::class) {
    group = "specialsource"

    mustRunAfter(shadowJar)
    minecraftVersion.set(mcversion)
    input.set(normalJarPath)
}

val motso = task("motso", com.baioretto.specialsource.task.MojangObfuscatedToSpigotObfuscated::class) {
    group = "specialsource"

    mustRunAfter(mmtmo)
    minecraftVersion.set(mcversion)
    input.set(obfuscatedJarPath)
}

val copyJar = task("copyJar", Copy::class) {
    group = "specialsource"

    from(normalJarPath)
    findProperty("server.plugin.folder")?.let { into(it) }

    rename {
        "${project.name}.jar"
    }

    mustRunAfter(motso)
}

tasks.register("compile") {
    group = "specialsource"

    dependsOn(shadowJar)
    dependsOn(mmtmo)
    dependsOn(motso)
    dependsOn(copyJar)
}

val sourcesJar = task<Jar>("sourcesJar") {
}

val javadocJar = task<Jar>("javadocJar") {
    val javadoc = tasks.javadoc
    dependsOn.add(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ba1oretto/baiolib")
            credentials {
                username = findProperty("gpr.user") as String?
                password = findProperty("gpr.token") as String?
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            groupId = project.group as String
            artifactId = project.name.toLowerCase()
            version = project.version as String

            artifact(sourcesJar)
            artifact(javadocJar)
            from(components.getByName("java"))
        }
    }
}