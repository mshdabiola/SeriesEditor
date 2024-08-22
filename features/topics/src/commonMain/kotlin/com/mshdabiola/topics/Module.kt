package com.mshdabiola.topics

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val topicModule = module {
    viewModelOf(::TopicsViewModel)
}
