package org.kethereum.kethabi

import com.squareup.moshi.Moshi
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.kethereum.abi.EthereumABI
import org.kethereum.abi_codegen.toKotlinCode
import java.io.File

class Kethabi : Plugin<Project> {
    override fun apply(target: Project) {

        val kethabiTask = target.tasks.create("kethabi")
        val sourcePath = "src/main/abi/"

        kethabiTask.apply {
            group = "Build"
            description = "Generate Kotlin code bindings from ABIs in $sourcePath"
            inputs.dir(target.file(sourcePath))
            doLast {
                val outDir = target.buildDir.resolve("generated/kethabi")
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
            if (it.isDirectory)
                processDirectory(it, sourcePath, outDir)
            else
                processFile(it, sourcePath, outDir)
        }
    }

    private fun processFile(it: File, sourcePath: String, outDir: File) {
        val path = it.absolutePath.substringAfter(sourcePath).removeSuffix(it.name).removeSuffix("/")
        val packageName = path.replace("/", ".")
        println("generating ${it.nameWithoutExtension} in package $packageName")
        val abi = EthereumABI(it.readText(), Moshi.Builder().build())
        abi.toKotlinCode(it.nameWithoutExtension, packageName).writeTo(outDir)
    }
}
