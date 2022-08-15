import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow")
}

tasks.withType(ShadowJar::class) {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    archiveVersion.set(rootProject.version as String)
    archiveExtension.set("jar")
    minimize()
}