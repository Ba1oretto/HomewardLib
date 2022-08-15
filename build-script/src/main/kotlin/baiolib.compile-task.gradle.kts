import org.gradle.api.internal.file.copy.CopyAction
import org.gradle.api.internal.file.copy.FileCopyAction

plugins {
    id("com.github.johnrengelman.shadow")
    id("baiolib.compile-config")
    id("baiolib.shadow-config")
}

val shadowJar = tasks.getByName("shadowJar")

val copyJar = task<MultiCopy>("copyJar") {
    mustRunAfter(shadowJar)
    group = "baiolib"
    from(layout.buildDirectory.dir("libs/${rootProject.name}-${rootProject.version}.jar").get().asFile)
    (findProperty("server.plugin.folder") as String?)?.let(::destDir)
    separator("|")
    rename(".*(.jar)", "${rootProject.name}$1")
}

tasks.register("compile") {
    group = "baiolib"
    dependsOn(shadowJar)
    dependsOn(copyJar)
}

abstract class MultiCopy : Copy() {
    private lateinit var destinationDirectories: String

    private lateinit var directorySeparator: String

    private fun createCopyActions(): Array<CopyAction> {
        if (!::destinationDirectories.isInitialized) return arrayOf()

        val destDirList = destinationDirectories.split(if (::directorySeparator.isInitialized) directorySeparator else "|")

        if (destDirList.isEmpty()) {
            return arrayOf(FileCopyAction(fileLookup.getFileResolver(File(destinationDirectories))))
        }

        val fileCopyActions = mutableListOf<FileCopyAction>()
        destDirList.forEach { dir ->
            fileCopyActions.add(FileCopyAction(fileLookup.getFileResolver(File(dir))))
        }
        return fileCopyActions.toTypedArray()
    }

    fun destDir(destinationDirectories: String): MultiCopy {
        this.destinationDirectories = destinationDirectories
        return this
    }

    fun separator(directorySeparator: String): MultiCopy {
        this.directorySeparator = directorySeparator
        return this
    }

    @TaskAction
    override fun copy() {
        val copyActionExecutor = createCopyActionExecuter()
        val copyActions = createCopyActions()
        var didWork = true
        copyActions.forEach { action ->
            if (!copyActionExecutor.execute(rootSpec, action).didWork) didWork = false
        }
        setDidWork(didWork)
    }

    @OutputDirectory
    @Optional
    override fun getDestinationDir(): File {
        return File("")
    }
}