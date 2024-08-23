package com.mshdabiola.composeinstruction

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ciModule = module {
    viewModelOf(::CiViewModel)
}
