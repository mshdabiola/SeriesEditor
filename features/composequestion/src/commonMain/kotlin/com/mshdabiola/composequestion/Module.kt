package com.mshdabiola.composequestion

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cqModule = module {
    viewModelOf(::CqViewModel)
}
