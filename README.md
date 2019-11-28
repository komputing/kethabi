[![](https://jitpack.io/v/komputing/kethabi.svg)](https://jitpack.io/#komputing/kethabi)

What is it?
-----------

This is a gradle plugin to generate Kotlin bindings for Ethereum contracts.

How to use it?
--------------
To use it you must have kethabi in your buildscript classpath:

```kotlin
buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.komputing:kethabi:<INSERT CURRENT KETHABI VERSION>")
    }
}
```

And then apply the plugin:

```kotlin
plugins {
    id("kethabi")
}
```

Now kethabi will look into the `src/main/abi` folder for *.abi files (optionally accompanied by a *.config file).
It will then generate code based on these abi files. 
If the abi file starts with an underscore (_) the classes will be marked as internal.
The filename will determine the classname (if not changed via the config file).
The package name will be generated from the path the abi file is in. E.g. if the file is in `src/main/abi/org/foo/contracts` then the package name will be `org.foo.contracts`.

It will generate 3 classes: 
 * transaction generator
 * a class that uses RPC to be able directly call functions of the contract
 * transaction detector to check if a given transaction is a function call for the given contract function

Under the hood
--------------

It uses the [KEthereum module abi_codegen](https://github.com/komputing/KEthereum/tree/master/abi_codegen) which itself uses [KotlinPoet](https://square.github.io/kotlinpoet) to generate the code.

Example usages
--------------

 * [Görli pusher](https://github.com/goerli/goerli_pusher)
 * multiple modules in KEthereum - e.g. [the ERC20 one](https://github.com/komputing/KEthereum/tree/master/erc20)
