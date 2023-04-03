plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common-model"))
    implementation(project(":api-jackson"))

    testImplementation(kotlin("test"))
}