plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {

    jvm {}


    sourceSets{
        val coroutinesVersion: String by project
        val ydbSdkVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                implementation(project(":common-model"))
                implementation(project(":common-repo"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting{
            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }


        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation("com.yandex.ydb:ydb-sdk-core:$ydbSdkVersion")
                implementation("com.yandex.ydb:ydb-sdk-table:$ydbSdkVersion")
                implementation("com.yandex.ydb:ydb-sdk-auth-iam:$ydbSdkVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting{
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation(kotlin("test-junit"))
                implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
            }
        }
    }
}