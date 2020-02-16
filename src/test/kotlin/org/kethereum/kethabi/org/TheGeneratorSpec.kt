package org.kethereum.kethabi.org

import okio.Okio
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kethereum.kethabi.generatorSpecAdapter
class TheGeneratorSpec {
    @Test
    fun canParseSimpleSpec() {

        val jsonSource = Okio.source(javaClass.getResource("/simplegeneratorspec.json").openStream())
        val parsed = generatorSpecAdapter.fromJson(Okio.buffer(jsonSource))
        assertThat(parsed?.classPrefix).isEqualTo("prefix")
    }
}