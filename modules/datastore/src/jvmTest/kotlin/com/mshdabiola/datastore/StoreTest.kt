package com.mshdabiola.datastore

import com.mshdabiola.database.generalPath
import com.mshdabiola.datastore.di.datastoreModule
import com.mshdabiola.datastore.di.storePath
import com.mshdabiola.testing.testDispatcherModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class StoreTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {

        this.modules(datastoreModule, testDispatcherModule)
    }

    @Test
    fun userdata() = runTest {
        storePath = "$generalPath/test"
        println(storePath)
        val store: Store by inject<Store>()
        store.updateUserData { it.copy(userId = 2897) }
        println(store.userData.first())
    }
}
