plugins {
//    alias(libs.plugins.android.library)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
    // new
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)

    id("maven-publish")
    id("signing")
}

//android {
//    namespace = "cum.eidam.material_pickers"
//    compileSdk = 36
//
//    defaultConfig {
//        minSdk = 21
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//
//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
//    }
//}

//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("release") {
//                from(components["release"])
//
//                groupId = "com.github.eidam-slices"
//                artifactId = "material-pickers"
//                // new:
//                version = findProperty("VERSION_NAME") as String
//
//                pom {
//                    name.set("Material Pickers")
//                    description.set("Material-styled item pickers for Android - Jetpack Compose")
//                    url.set("https://github.com/eidam-slices/material-pickers")
//                    licenses {
//                        license {
//                            name.set("The Apache License, Version 2.0")
//                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
//                            distribution.set("repo")
//                        }
//                    }
//                    developers {
//                        developer {
//                            id.set("eidam-slices")
//                            name.set("Eidam")
//                            url.set("https://github.com/eidam-slices")
//                        }
//                    }
//                    scm {
//                        url.set("https://github.com/eidam-slices/material-pickers")
//                        connection.set("scm:git@github.com:eidam-slices/material-pickers.git")
//                        developerConnection.set("scm:git@github.com:eidam-slices/material-pickers.git")
//                    }
//
//                }
//            }
//        }
//        signing {
//            useGpgCmd()
//            sign(publishing.publications["release"])
//        }
//    }
//}

kotlin {

    jvm()
    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "cz.eidam.material_pickers"
        compileSdk = 36
    }


    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
        }
    }

}