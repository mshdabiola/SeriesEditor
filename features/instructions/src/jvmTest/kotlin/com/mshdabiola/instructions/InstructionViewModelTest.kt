/*
 *abiola 2022
 */

package com.mshdabiola.instructions

import app.cash.turbine.test
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.instructions
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

class InstructionViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val instructionRepository by inject<IInstructionRepository>()

    @Test
    fun init() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = InstructionsViewModel(
            1,
            instructionRepository,
        )

        viewModel
            .instructions
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                assertEquals(
                    state.data.size,
                    1,
                )

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun delete() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = InstructionsViewModel(
            1,
            instructionRepository,
        )

        viewModel
            .instructions
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                viewModel.onDelete(1)

                state = awaitItem()

                assertTrue(state is Result.Success)

                assertEquals(
                    state.data.size,
                    0,
                )

                cancelAndIgnoreRemainingEvents()
            }
    }
}
