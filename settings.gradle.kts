rootProject.name = "tax-residency-tracker"

pluginManagement{
    plugins{
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion apply false
    }
}