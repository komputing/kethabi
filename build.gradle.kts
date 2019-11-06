plugins {
    `kotlin-dsl`
    maven
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven("https://www.jitpack.io")
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.4.3")
    implementation("com.squareup.moshi:moshi:1.8.0")
    implementation("com.github.komputing.kethereum:abi:0.76.8")
    implementation("com.github.komputing.kethereum:abi_codegen:0.76.8")
}
