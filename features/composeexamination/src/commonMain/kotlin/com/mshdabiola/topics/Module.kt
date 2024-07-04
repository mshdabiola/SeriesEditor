package com.mshdabiola.topics

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val composeExamModule = module {
    viewModelOf(::ComposeExaminationViewModel)
}
