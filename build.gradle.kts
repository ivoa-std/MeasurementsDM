
plugins {
    id("net.ivoa.vo-dml.vodmltools") version "0.5.10"
    `maven-publish`
}

group = "org.javastro.ivoa.dm"
version = "1.0-SNAPSHOT"



vodml {
    vodmlDir.set(file("vo-dml"))
    bindingFiles.setFrom(file("vo-dml/MeasurementBinding.xml"))
}

dependencies {
    api("org.javastro.ivoa.dm:coordinateDM:1.1.1-SNAPSHOT") //reusing the "standard" base library
    // Use JUnit Jupiter for testing - only necessary if you want to write unit tests!
    implementation("org.slf4j:slf4j-api:1.7.32")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.javastro:jaxbjpa-utils:0.1.2:test")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
