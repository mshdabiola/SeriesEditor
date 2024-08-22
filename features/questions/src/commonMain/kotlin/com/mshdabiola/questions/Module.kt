package com.mshdabiola.questions

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val questionsModule = module {
    viewModelOf(::QuestionsViewModel)
}
