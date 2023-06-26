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
        val testContainersVersion: String by project


        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation(project(":tx-common"))

                implementation("com.yandex.ydb:ydb-sdk-core:$ydbSdkVersion")
                implementation("com.yandex.ydb:ydb-sdk-table:$ydbSdkVersion")
                api("com.yandex.ydb:ydb-sdk-auth-iam:$ydbSdkVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting{
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation(project(":tx-common"))
                implementation(kotlin("test-junit"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
                implementation("org.testcontainers:testcontainers:$testContainersVersion")
            }
        }
    }
}