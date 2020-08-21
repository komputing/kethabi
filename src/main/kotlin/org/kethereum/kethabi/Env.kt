package org.kethereum.kethabi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kethereum.abi_codegen.model.GeneratorSpec


internal val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
internal val generatorSpecAdapter = moshi.adapter(GeneratorSpec::class.java)

// https://github.com/komputing/kethabi/issues/6
internal const val kethereum_version = "0.83.0"