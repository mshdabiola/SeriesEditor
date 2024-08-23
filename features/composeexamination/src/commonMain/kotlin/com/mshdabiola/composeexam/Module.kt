package com.mshdabiola.composeexam

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val composeExamModule = module {
    viewModelOf(::ComposeExaminationViewModel)
}
