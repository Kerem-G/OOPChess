plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.chess"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Revisit--patch found online--something about .kts files working differently?
application {
    mainClass.set("org.chess.ChessApp")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform {
        excludeTags("manual")
    }
}