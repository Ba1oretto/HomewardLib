plugins {
    id("io.freefair.lombok")
    id("baiolib.compile-task")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public") // papermc
}

dependencies {
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.85.2")


    // (findProperty("maven.local.file") as String?)?.let { paths ->
    //     compileOnly(files(paths.split("|").toTypedArray()))
    // } // local
}