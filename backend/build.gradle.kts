plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jooq.jooq-codegen-gradle") version "3.21.9"
	id("com.github.bjornvester.wsdl2java") version "2.0.4"
}

group = "biz.thehacker"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

jooq {
	configuration {
		generator {
			name = "org.jooq.codegen.JavaGenerator"

			database {
				name = "org.jooq.meta.extensions.ddl.DDLDatabase"
				properties {
					property {
						key = "scripts"
						value = "src/main/resources/schema.sql"
					}

					property {
						key = "defaultNameCase"
						value = "upper"
					}
				}
			}

			target {
				packageName = "biz.thehacker.demo.earsystem"
				directory = "build/generated-sources/jooq"
			}
		}
	}
}

wsdl2java {
	wsdlDir.set(layout.projectDirectory.dir("src/main/resources"))
	includes.set(
		listOf(
			"wsdl/MitteilungsService.wsdl"
		)
	)
	packageName.set("biz.thehacker.demo.earsystem.soap.mitteilung")
	cxfVersion.set("4.2.3")
}

repositories {
	mavenCentral()
}

dependencies {
	// Web

	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	// Web Services / SOAP

	implementation("org.springframework.boot:spring-boot-starter-webservices")
	implementation("com.sun.xml.ws:jaxws-rt:4.0.5")

	// Persistence and database

	implementation("org.springframework.boot:spring-boot-starter-jooq")
	jooqCodegen("org.jooq:jooq-meta-extensions:3.21.9")

	implementation("org.springframework.boot:spring-boot-h2console") // TODO remove in production
	runtimeOnly("com.h2database:h2")

	// Tests

	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webservices-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// IDE insight

	implementation("org.jetbrains:annotations:26.1.0")
}

tasks.named<JavaCompile>("compileJava") {
	dependsOn(":jooqCodegen")
	dependsOn(":wsdl2java")
}

tasks.withType<Test> {
	useJUnitPlatform {
		excludeTags("manual")
	}
}
