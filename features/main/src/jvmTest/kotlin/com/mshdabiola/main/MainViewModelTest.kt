/*
 *abiola 2022
 */

package com.mshdabiola.main

import app.cash.turbine.test
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.util.MainDispatcherRule
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
    private val subjectRepository by inject<ISubjectRepository>()
    private val examinationRepository by inject<IExaminationRepository>()
    private val userdataRepository by inject<UserDataRepository>()

    @Test
    fun init() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            subjectRepository,
            examinationRepository,
            userdataRepository,
            1,
        )

        viewModel
            .examUiMainState
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                assertEquals(
                    10,
                    state.data.size,

                )

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun delete() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            subjectRepository,
            examinationRepository,
            userdataRepository,
            1,
        )

        viewModel
            .examUiMainState
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                viewModel.onDeleteExam(1)

                state = awaitItem()

                assertTrue(state is Result.Success)

                assertEquals(
                    9,
                    state.data.size,

                )

                cancelAndIgnoreRemainingEvents()
            }
    }
}
