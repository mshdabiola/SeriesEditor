package com.mshdabiola.composetopic

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ctModule = module {
    viewModelOf(::CtViewModel)
}
