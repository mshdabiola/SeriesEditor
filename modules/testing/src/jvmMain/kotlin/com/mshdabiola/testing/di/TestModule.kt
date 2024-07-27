/*
 *abiola 2022
 */

package com.mshdabiola.testing.di

import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.di.dataModule
import com.mshdabiola.datastore.di.datastoreModule
import com.mshdabiola.datastore.di.storePath
import com.mshdabiola.model.generalPath
import com.mshdabiola.testing.databaseTestModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.dsl.bind
import org.koin.dsl.module

val testDispatcherModule =
    module {
        single {
            storePath = "$generalPath/testing"

            UnconfinedTestDispatcher()
        } bind CoroutineDispatcher::class
    }

val dataTestModule = module {
    includes(datastoreModule, databaseTestModule, testDispatcherModule, analyticsModule)
    dataModule()
}
