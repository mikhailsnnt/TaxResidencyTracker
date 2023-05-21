plugins {
    kotlin("multiplatform")
}

kotlin{

    js(IR) {
        nodejs()
    }


    jvm {
        withJava()
    }


    sourceSets{
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting{
            dependencies{
                implementation(kotlin("stdlib-common"))
                implementation(project(":common-model"))
                implementation(project(":api-multi"))
                implementation("org.jetbrains.kotlin:kotlin-reflect")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting{
            dependencies{
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}