/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import co.touchlab.kermit.Logger
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.exportableData
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val seriesRepository by inject<ISeriesRepository>()
    private val userdataRepository by inject<UserDataRepository>()
    private val subjectRepository by inject<ISubjectRepository>()
    private val logger = Logger

}
