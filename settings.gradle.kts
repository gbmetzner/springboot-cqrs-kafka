pluginManagement {

    val springBootPluginVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    val lombokPluginVersion: String by settings
    val spotlessPluginVersion: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    plugins {
        id("org.springframework.boot") version springBootPluginVersion
        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        id("io.freefair.lombok") version lombokPluginVersion
        id("com.diffplug.spotless") version spotlessPluginVersion
    }

}

rootProject.name = "springboot-cqrs-kafka"
include("account-common")
include("cqrs-core")
include("account-cmd-service")
include("account-query-service")