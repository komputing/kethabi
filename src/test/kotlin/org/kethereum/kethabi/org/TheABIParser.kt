package org.kethereum.kethabi.org

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kethereum.abi.EthereumABI
import org.kethereum.kethabi.moshi

class TheABIParser {

    // similar to the test in kethereum/abi - but here a newer moshi is used
    // that can (and did) fuck things uo
    @Test
    fun canParseSimpleABI() {
        listOf("/peepeth.abi" to 31,
                "/ENS.abi" to 11,
                "/ENSResolver.abi" to 29,
                "/MCD.abi" to 8,
                "/ERC20.abi" to 12
        ).forEach {
            val json = javaClass.getResource(it.first).readText()
            val abi = EthereumABI(json, moshi)
            assertThat(abi.methodList.size).isEqualTo(it.second)
        }
    }
}