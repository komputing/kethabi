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
    implementation("com.squareup:kotlinpoet:1.4.3")
    implementation("com.squareup.moshi:moshi:1.8.0")
    implementation("com.github.komputing.kethereum:abi:0.77.0")
    implementation("com.github.komputing.kethereum:abi_codegen:0.77.0")
}
