// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.android.library) apply false

}
//repositories {
//    google() // For Android libraries
//    mavenCentral() // For Kotlin libraries
//}