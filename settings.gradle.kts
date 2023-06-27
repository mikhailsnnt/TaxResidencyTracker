rootProject.name = "tax-residency-tracker"

pluginManagement{
    plugins{
        val kotlinVersion: String by settings
        val openapiVersion: String by settings
        val ktorVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
    }
}

include("app-ktor")
include("tx-biz")
include("api-jackson")
include("api-multi")
include("common-logging")
include("common-mappers")
include("lib-cor")
include("tx-common")
include("repo-ydb")
