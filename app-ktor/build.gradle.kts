import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val serializationVersion: String by project
val logbackVersion: String by project



// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    kotlin("multiplatform")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.0")
    implementation("io.ktor:ktor-server-websockets-jvm:2.3.0")
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

kotlin{

    jvm {
        withJava()
    }
//    val nativeTarget = when (System.getProperty("os.name")) {
//        "Linux" -> linuxX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }
//
//    nativeTarget.apply {
//        binaries {
//            executable {
//                entryPoint = "io.ktor.server.cio.main"
//            }
//        }
//    }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":api-multi"))
                implementation(project(":common-mappers"))
                implementation(project(":common-logging"))
                implementation(project(":repo-ydb"))
                implementation(project(":tx-common"))
                implementation(project(":tx-biz"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation(ktor("core"))
                implementation(ktor("websockets"))
                implementation(ktor("cio"))
                implementation(ktor("caching-headers"))
                implementation(ktor("cors"))
                implementation(ktor("config-yaml"))
                implementation(ktor("content-negotiation"))

                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")

            }
        }


        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting{
            dependencies{
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation(project(":tx-common"))
                implementation(project(":repo-ydb"))

            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}