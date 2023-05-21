plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {
        withJava()
    }
    js(IR) {
        nodejs()
    }

    sourceSets{
        val datetimeVersion: String by project

        @Suppress("unused")
        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))

                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
    }
}