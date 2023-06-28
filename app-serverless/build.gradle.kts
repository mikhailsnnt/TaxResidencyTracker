plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.yandex.cloud:java-sdk-functions:2.5.1")

    val jacksonVersion: String by project
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // transport models
    implementation(project(":api-jackson"))
    implementation(project(":common-mappers"))
    implementation(project(":repo-ydb"))
    implementation(project(":tx-common"))
    implementation(project(":tx-biz"))

    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

}