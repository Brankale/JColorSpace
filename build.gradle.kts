plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.github.brankale"
version = "1.0"

dependencies {
    implementation(libs.ejml)
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}