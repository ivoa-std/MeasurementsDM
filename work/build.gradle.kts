plugins {
    id("net.ivoa.vo-dml.vodmltools") version "0.5.14"
    `maven-publish`
    application
}

group = "org.javastro.ivoa.dm"
version = "1.0-SNAPSHOT"

vodml {
    vodslDir.set(file("../model"))
    vodmlDir.set(file("../vo-dml"))
    bindingFiles.setFrom(file("../vo-dml/meas.vo-dml.binding.xml"))
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

application {
    mainClass.set("Genschema")
}

# For conversion of UML Tool output to VO-DML/XML via translation script
tasks.register("UmlToVodml", net.ivoa.vodml.gradle.plugin.XmiTask::class.java) {
    xmiScript.set("xmi2vo-dml_Modelio3.7_UML2.4.1.xsl") // the conversion script
    xmiFile.set(file("../model/coords_1.0_uml2p4p1.xmi")) //the UML XMI to convert
    vodmlFile.set(file("test.vo-dml.xml")) // the output VO-DML file.
    description = "convert UML to VO-DML"
}
