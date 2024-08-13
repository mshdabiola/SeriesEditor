package com.mshdabiola.examinations

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val examModule = module {

    viewModelOf(::ExamViewModel)
}
