import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(projects.core.di)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)
            //implementation(projects.core.di)
            implementation(libs.androidx.activity)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
//            implementation(compose.material3)
            implementation(libs.jetbrians.material3)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.org.jetbrains.navigation)
            implementation(libs.kotlinx.serialization.json)

            //implementation(projects.core.di)
            api(projects.core.di)
            implementation(projects.core.ui.res)
            implementation(projects.core.ui.components)
            implementation(projects.core.model)
            implementation(projects.core.domain)
            implementation(projects.core.logger)
            implementation(projects.core.persistance.datastore)

            implementation(libs.koin.compose.viewmodel)
            implementation(compose.components.resources)


            implementation(projects.feature.auth.login)
            implementation(projects.feature.dashboard)
            implementation(projects.feature.home)
            implementation(projects.feature.banking)
            implementation(projects.feature.transactionHistory)
            implementation(projects.feature.menu)
            implementation(projects.feature.bankTransfer)
            implementation(projects.feature.otpVerification)
            implementation(projects.feature.qrScanner)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        nativeMain.dependencies {
            //implementation(projects.core.di)
        }

        iosMain.dependencies {
            //implementation(projects.core.di)
        }
    }
}

android {
    namespace = "org.rajesh.mobile_banking"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.rajesh.mobile_banking"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    debugImplementation(compose.uiTooling)
}
