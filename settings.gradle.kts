rootProject.name = "tribot-script-template"

//include("libraries:my-library")
//include("libraries:framework")

//include("scripts:my-script")
include("scripts:sample")
include("scripts:verify")
//include("scripts")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}