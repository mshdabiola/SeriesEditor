package com.mshdabiola.instructions

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val instructionsModule = module {
    viewModelOf(::InstructionsViewModel)
}
