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
        val loggingVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))

                api("org.lighthousegames:logging:$loggingVersion")
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