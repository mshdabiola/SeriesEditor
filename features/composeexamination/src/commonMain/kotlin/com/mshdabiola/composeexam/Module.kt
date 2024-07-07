package com.mshdabiola.composeexam

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val composeExamModule = module {
    viewModelOf(::ComposeExaminationViewModel)
}
