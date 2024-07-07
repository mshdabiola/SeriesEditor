package com.mshdabiola.composequestion

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val cqModule = module {
    viewModelOf(::CqViewModel)
}
