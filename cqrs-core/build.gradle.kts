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



repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.logging.log4j:log4j-api:2.24.3")
	implementation("org.apache.logging.log4j:log4j-core:2.24.3")
	implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.3")
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