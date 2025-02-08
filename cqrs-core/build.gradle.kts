plugins {
	java
	id("io.freefair.lombok") version "8.12.1"
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

}

tasks.withType<Test> {
	useJUnitPlatform()
}
