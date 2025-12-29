rootProject.name = "mobileBanking"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core:network")
include(":core:data")
include(":core:di")
include(":core:domain")
include(":core:model")
include(":core:crypto")
include(":core:logger")

include(":core:networkHelper")


include(":feature:auth:login")
include(":feature:splashscreen")
include(":core:persistance:datastore")
include(":core:ui:components")
include(":core:ui:res")
include(":feature:dashboard")
include(":feature:home")
include(":feature:banking")
include(":feature:transactionHistory")
include(":feature:menu")
include(":feature:user")
include(":core:utils")
include(":feature:bankTransfer")
include(":core:navigation")
include(":feature:loadWallet")
include(":feature:confirmation")
include(":feature:paymentAuthentication")
include(":feature:userAccounts")
