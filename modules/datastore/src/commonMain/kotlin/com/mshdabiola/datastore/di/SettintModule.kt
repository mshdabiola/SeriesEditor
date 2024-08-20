package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single {
        StoreImpl(
            userdata = get(qualifier = qualifier("userdata")),
            question = get(qualifier = qualifier("question")),
            instruction = get(qualifier = qualifier("instruction")),
            coroutineDispatcher = get(),
        )
    } bind Store::class
}
