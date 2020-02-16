val kethereum_version = "0.79.4"

apply {
    from("https://raw.githubusercontent.com/ligi/gradle-common/master/versions_plugin_stable_only.gradle")
}

buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    }
}



plugins {
    `kotlin-dsl`
    maven
}

repositories {
    jcenter()
    maven("https://www.jitpack.io")
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.5.0")
    implementation("com.squareup.moshi:moshi:1.8.0")
    implementation("com.github.komputing.kethereum:abi:$kethereum_version")
    implementation("com.github.komputing.kethereum:abi_codegen:$kethereum_version")
}
