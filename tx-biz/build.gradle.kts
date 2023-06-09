plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {

    jvm {}

    js(IR) {
        nodejs()
    }

//    linuxX64 {}

    sourceSets{
        val coroutinesVersion: String by project
        val atomicfuVersion: String by project
        val kotlinDateTimeVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinDateTimeVersion")

                implementation(project(":lib-cor"))
                implementation(project(":common-logging"))
                implementation(project(":tx-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting{
            dependencies{
                implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}