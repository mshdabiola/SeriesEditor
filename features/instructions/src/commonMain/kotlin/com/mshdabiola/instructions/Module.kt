package com.mshdabiola.instructions

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val instructionsModule = module {
    viewModelOf(::InstructionsViewModel)
}
