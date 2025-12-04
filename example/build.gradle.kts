import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
}

dependencies {
    implementation(project(":analyzer"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val jarTask = tasks.named<Jar>("jar") {
    manifest {
        attributes("Main-Class" to "org.example.target.Main")
    }
    archiveFileName.set("example-target.jar")
}

tasks.test {
    useJUnitPlatform()

    dependsOn(jarTask)

    systemProperty("test.jar.path", jarTask.get().archiveFile.get().asFile.absolutePath)
}
