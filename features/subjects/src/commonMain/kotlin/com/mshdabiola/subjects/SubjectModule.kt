package com.mshdabiola.subjects

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val subjectModule = module {

    viewModelOf(::SubjectViewModel)
}
