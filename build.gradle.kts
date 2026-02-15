plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false

    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose.multiplatform) apply false

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false

    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

//nexusPublishing {
//    repositories {
//        sonatype {
//            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
//            username.set(findProperty("OSSRH_USERNAME") as String)
//            password.set(findProperty("OSSRH_PASSWORD") as String)
//            stagingProfileId.set("io.github.eidam-slices")
//        }
//    }
//}
