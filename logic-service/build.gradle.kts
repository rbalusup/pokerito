import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    java
    idea
    id("com.google.protobuf") version "0.8.8"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "io.toxa108.io.pokerito"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8


repositories {
    maven("https://plugins.gradle.org/m2/")
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.google.protobuf:protobuf-java:3.6.1")
    implementation("io.grpc:grpc-stub:1.15.1")
    implementation("io.grpc:grpc-protobuf:1.15.1")
    implementation("io.grpc:grpc-all:1.15.0")
    implementation("io.grpc:grpc-services:1.15.1")

    if (JavaVersion.current().isJava9Compatible) {
        // Workaround for @javax.annotation.Generated
        // see: https://github.com/grpc/grpc-java/issues/3633
        compile("javax.annotation:javax.annotation-api:1.3.1")
    }

    // Extra proto source files besides the ones residing under
    // "src/main".
    protobuf(files("lib/protos.tar.gz"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testProtobuf(files("lib/protos-test.tar.gz"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
    plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
            }
        }
    }
}
val protoCopy = tasks.register("protoCopy") {
    doLast {

        copy {
//            delete ("/home/toxa/Work/java/github/test-frontend/proto-gen")
            from ("build/generated/source/proto")
            into ("/home/toxa/Work/java/github/test-frontend/build/generated/source/proto")
            println("Generated .proto were copied to test-frontend")
        }

        copy {
//            delete ("/home/toxa/Work/java/github/user-service/proto-gen")
            from ("build/generated/source/proto")
            into ("/home/toxa/Work/java/github/user-service/build/generated/source/proto")
            println("Generated .proto were copied to user-service")
        }

        copy {
            from ("build/generated/source/proto")
            into ("/home/toxa/Work/java/github/table-service/build/generated/source/proto")
            println("Generated .proto were copied to table-service")
        }

        copy {
            from ("build/generated/source/proto")
            into ("/home/toxa/Work/java/github/dealer-service/build/generated/source/proto")
            println("Generated .proto were copied to dealer-service")
        }
    }
}

tasks.build {
    dependsOn(protoCopy)
}