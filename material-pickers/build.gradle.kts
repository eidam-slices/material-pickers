plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)

    alias(libs.plugins.vanniktech.maven.publish)
}

group = "io.github.eidam-slices"
version = "0.4.0"

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()


    coordinates(artifactId = "material-pickers")

    pom {
        name.set("Material Pickers")
        description.set("Material 3 friendly item pickers for Compose Multiplatform (Android, iOS, Desktop)")
        url.set("https://github.com/eidam-slices/material-pickers")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("eidam-slices")
                name.set("Eidam")
                url.set("https://github.com/eidam-slices")
                email.set("eidam.slices@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/eidam-slices/material-pickers")
            connection.set("scm:git:git://github.com/eidam-slices/material-pickers.git")
            developerConnection.set("scm:git:ssh://git@github.com:eidam-slices/material-pickers.git")

        }
    }
}

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