package com.mshdabiola.questions

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val questionsModule = module {
    viewModelOf(::QuestionsViewModel)
}
