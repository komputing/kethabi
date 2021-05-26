package org.kethereum.kethabi.org

import okio.buffer
import okio.source
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kethereum.kethabi.generatorSpecAdapter

class TheGeneratorSpecParser {
    @Test
    fun canParseSimpleSpec() {

        val jsonSource = javaClass.getResource("/simplegeneratorspec.json").openStream().source()
        val parsed = generatorSpecAdapter.fromJson(jsonSource.buffer())
        assertThat(parsed?.classPrefix).isEqualTo("prefix")
    }

}