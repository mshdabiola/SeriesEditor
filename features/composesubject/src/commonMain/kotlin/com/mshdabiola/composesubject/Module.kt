package com.mshdabiola.composesubject

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val subjectModule = module {
    viewModelOf(::ComposeSubjectViewModel)
}
