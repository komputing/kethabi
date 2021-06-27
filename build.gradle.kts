// https://github.com/komputing/kethabi/issues/6
val kethereumVersion = "0.83.6"

apply {
    from("https://raw.githubusercontent.com/ligi/gradle-common/master/versions_plugin_stable_only.gradle")
}

buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.ben-manes:gradle-versions-plugin:0.39.0")
    }
}

repositories {
    jcenter()
    maven("https://www.jitpack.io")
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("maven-publish")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.8.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")

    implementation("com.github.komputing.kethereum:abi:$kethereumVersion")
    implementation("com.github.komputing.kethereum:abi_codegen:$kethereumVersion")

    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.mockk:mockk:1.11.0")

}
