package org.kethereum.kethabi

import okio.buffer
import okio.source
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.AbstractCompile
import org.kethereum.abi.EthereumABI
import org.kethereum.abi_codegen.model.GeneratorSpec
import org.kethereum.abi_codegen.toKotlinCode
import java.io.File
import java.net.URI

class Kethabi : Plugin<Project> {
    override fun apply(target: Project) {

        val kethabiTask = target.tasks.create("kethabi")
        val sourcePath = "src/main/abi/"
        kethabiTask.apply {
            val generatedOutput = project.layout.buildDirectory.dir("generated").map { it.dir("kethabi") }

            group = "Build"
            description = "Generate Kotlin code bindings from ABIs in $sourcePath"
            inputs.dir(project.file(sourcePath))
            outputs.dir(generatedOutput)
            val sources = project.properties["sourceSets"] as SourceSetContainer

            sources.getByName("main").apply {
                output.dir(mapOf("builtBy" to kethabiTask), generatedOutput)
                java.srcDir(generatedOutput)
            }

            target.addJitpackRepository()
            target.addDependencies()

            doLast {

                val outDir = generatedOutput.get().asFile
                outDir.deleteRecursively()

                println("generating kethabi code to $outDir")

                processDirectory(target.file(sourcePath), sourcePath, outDir)
            }
        }

        target.tasks.withType(AbstractCompile::class.java).forEach {
            it.dependsOn(kethabiTask)
        }
    }

    private fun processDirectory(target: File, sourcePath: String, outDir: File) {
        target.listFiles()?.forEach {
            when {
                it.isDirectory -> processDirectory(it, sourcePath, outDir)
                it.extension.toLowerCase() == "abi" -> processABIFile(it, sourcePath, outDir)
                it.extension.toLowerCase() == "config" -> if (!File(it.nameWithoutExtension + ".abi").exists()) {
                    println("!!Warning!! found a config file (${it.path} in the ABI path that does not have the corresponding abi file")
                }
                else -> println("!!Warning!! found a file (${it.path} in the ABI path that is neither an ABI file nor a config")
            }
        }
    }

    private fun processABIFile(it: File, sourcePath: String, outDir: File) {
        val path = it.absolutePath.substringAfter(sourcePath).removeSuffix(it.name).removeSuffix("/")
        val packageName = path.replace("/", ".")
        val abi = EthereumABI(it.readText(), moshi)
        val makeInternal = it.nameWithoutExtension.startsWith("_")
        val className = it.nameWithoutExtension.removePrefix("_")

        val configFile = File(it.nameWithoutExtension + ".config")
        val spec = if (!configFile.exists()) {
            GeneratorSpec(className, packageName, makeInternal)
        } else {
            println("found config ${configFile.name}")

            generatorSpecAdapter.fromJson(configFile.source().buffer())
        } ?: throw IllegalArgumentException("Could not generator config config from file ${configFile.name}")

        println("generating $className in package $packageName with generatorSpec:$spec")

        abi.toKotlinCode(spec).writeTo(outDir)
    }

    private fun Project.addJitpackRepository() = repositories.maven {
        it.name = "jitpack"
        it.url = URI("https://maven.aliyun.com/repository/central")
    }

    private fun Project.addDependencies() {
        project.dependencies.add("implementation", "com.github.komputing.kethereum:rpc:$kethereum_version")
        project.dependencies.add("implementation", "com.github.komputing.kethereum:model:$kethereum_version")
        project.dependencies.add("implementation", "com.github.komputing.kethereum:types:$kethereum_version")
        project.dependencies.add("implementation", "com.github.komputing:khex:1.1.2")
    }
}
