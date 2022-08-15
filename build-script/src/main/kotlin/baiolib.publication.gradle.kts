plugins {
    `maven-publish`
    `java-library`
}

val sourceJar = task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
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
                username = providers.gradleProperty("gpr.user").orNull
                password = providers.gradleProperty("gpr.token").orNull
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            groupId = rootProject.group as String
            artifactId = rootProject.name.toLowerCase()
            version = rootProject.version as String

            artifact(sourceJar)

            artifact(javadocJar)

            from(components.getByName("java"))
        }
    }
}