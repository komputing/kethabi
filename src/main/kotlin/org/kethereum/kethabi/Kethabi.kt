package org.kethereum.kethabi

import com.squareup.moshi.Moshi
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.kethereum.abi.EthereumABI
import org.kethereum.abi_codegen.toKotlinCode
import java.io.File

class Kethabi : Plugin<Project> {
    override fun apply(target: Project) {

        val outDir = target.buildDir.resolve("generated/kethabi")
        outDir.deleteRecursively()

        println("generating kethabi code to $outDir")

        val sourcePath = "src/main/abi/"
        processDirectory(target.file(sourcePath), sourcePath, outDir)

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
