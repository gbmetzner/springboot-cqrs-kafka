plugins {
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

group = "com.gbm"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":cqrs-core"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        removeUnusedImports()
        eclipse()
        formatAnnotations()
    }
}
