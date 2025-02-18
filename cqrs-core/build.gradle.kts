import org.gradle.kotlin.dsl.getByName
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("io.freefair.lombok")
	id("com.diffplug.spotless")
}

group = "com.gbm"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.apache.logging.log4j:log4j-api:2.24.3")
	implementation("org.apache.logging.log4j:log4j-core:2.24.3")
	implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.3")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<BootJar>("bootJar") {
	enabled = false
}

tasks.withType<JavaCompile> {
	dependsOn("spotlessApply")
}

spotless {
	java {
		removeUnusedImports()
		eclipse()
		formatAnnotations()
	}
}