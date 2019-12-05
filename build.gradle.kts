val kethereum_version = "0.79.4"

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
    implementation("com.squareup:kotlinpoet:1.4.4")
    implementation("com.squareup.moshi:moshi:1.8.0")
    implementation("com.github.komputing.kethereum:abi:$kethereum_version")
    implementation("com.github.komputing.kethereum:abi_codegen:$kethereum_version")
}
