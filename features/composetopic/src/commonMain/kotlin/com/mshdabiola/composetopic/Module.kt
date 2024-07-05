package com.mshdabiola.composetopic

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ctModule = module {
    viewModelOf(::CtViewModel)
}
