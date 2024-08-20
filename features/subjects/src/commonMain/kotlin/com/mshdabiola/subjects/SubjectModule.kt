package com.mshdabiola.subjects

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val subjectModule = module {

    viewModelOf(::SubjectViewModel)
}
