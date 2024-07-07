package com.mshdabiola.composeinstruction

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ciModule = module {
    viewModelOf(::CiViewModel)
}
