import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by rootProject.extra { "1.3.10" }
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0-alpha07")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {

    repositories {
        google()
        jcenter()
        maven { url = URI("https://jitpack.io") }
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
