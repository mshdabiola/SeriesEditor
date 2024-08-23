package com.mshdabiola.examinations

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val examModule = module {

    viewModelOf(::ExamViewModel)
}
