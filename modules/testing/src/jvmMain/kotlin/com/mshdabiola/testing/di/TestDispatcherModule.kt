/*
 *abiola 2022
 */

package com.mshdabiola.testing.di

import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.di.dataModule
import com.mshdabiola.datastore.createDataStoreInstruction
import com.mshdabiola.datastore.createDataStoreQuestion
import com.mshdabiola.datastore.createDataStoreUserData
import com.mshdabiola.datastore.di.commonModule
import com.mshdabiola.model.generalPath
import com.mshdabiola.testing.databaseTestModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

private val testDispatcherModule =
    module {
    single { UnconfinedTestDispatcher() } bind CoroutineDispatcher::class
}
val userPath="$generalPath/userdata"

private val dataStoreTest=module {
    includes(commonModule)

    single(qualifier = qualifier("userdata")) {
        createDataStoreUserData { "$userPath/userdataE" }
    }

    single(qualifier = qualifier("question")) {
        createDataStoreQuestion { "$userPath/questions" }
    }

    single(qualifier = qualifier("instruction")) {
        createDataStoreInstruction { "$userPath/instructions" }
    }
}
val dataTestModule= module {
    includes(dataStoreTest, databaseTestModule, testDispatcherModule , analyticsModule)
    dataModule()
}