// https://github.com/komputing/kethabi/issues/6
val kethereumVersion = "0.81.1"

apply {
    from("https://raw.githubusercontent.com/ligi/gradle-common/master/versions_plugin_stable_only.gradle")
}

buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.ben-manes:gradle-versions-plugin:0.29.0")
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

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.5.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")

    implementation("com.github.komputing.kethereum:abi:$kethereumVersion")
    implementation("com.github.komputing.kethereum:abi_codegen:$kethereumVersion")

    testImplementation("org.assertj:assertj-core:3.15.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.mockk:mockk:1.9.3")

}
