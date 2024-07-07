package com.mshdabiola.composesubject

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val subjectModule = module {
    viewModelOf(::ComposeSubjectViewModel)
}
