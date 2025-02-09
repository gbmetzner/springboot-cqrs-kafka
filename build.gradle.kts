allprojects {

    repositories {
        mavenCentral()
        mavenLocal()
    }

}

subprojects {

    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
        options.compilerArgs.add("-Xlint:all")
        options.compilerArgs.add("-Xlint:-deprecation")
        options.release.set(21)
    }

}