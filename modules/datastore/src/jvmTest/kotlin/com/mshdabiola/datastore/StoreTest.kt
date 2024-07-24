package com.mshdabiola.datastore

import com.mshdabiola.datastore.di.commonModule
import com.mshdabiola.model.generalPath
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class StoreTest :KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    val userPath="$generalPath/temp/userdata"

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        val modules= module {
            single { UnconfinedTestDispatcher() } bind CoroutineDispatcher::class

            single(qualifier = qualifier("userdata")) {
                    createDataStoreUserData { userPath}
                }

                single(qualifier = qualifier("question")) {
                    createDataStoreQuestion { "$generalPath/questions" }
                }

                single(qualifier = qualifier("instruction")) {
                    createDataStoreInstruction { "$generalPath/instructions" }
                }
            }


        this.modules(modules,commonModule,)
    }

    @Test
    fun userdata()=runTest{
        val store: Store by inject<Store>()
        println(generalPath)
      //  store.updateUserData { it.copy(userId = 2897) }
        println( store.userData.first())

    }
}