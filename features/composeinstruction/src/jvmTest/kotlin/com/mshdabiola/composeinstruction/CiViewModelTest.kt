/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.ISettingRepository
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

class CiViewModelTest : KoinTest {

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
    private val settingRepository by inject<ISettingRepository>()

    @Test
    fun init_update() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CiViewModel(1, 1, instructionRepository, settingRepository)

        viewModel
            .ciState
            .test {
                var state = awaitItem()

                assertTrue(state is CiState.Loading)

                state = awaitItem()

                assertTrue(state is CiState.Success)

                val default = exportableData
                val instruction = default.instructions[0]

                assertEquals(viewModel.title.text.toString(), instruction.title)
                assertEquals(state.content.size, instruction.content.size)
                assertEquals(state.id, 1)
                assertEquals(state.examId, 1)
            }
    }

    @Test
    fun update() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CiViewModel(2, 2, instructionRepository, settingRepository)

        viewModel
            .ciState
            .test {
                var state = awaitItem()

                assertTrue(state is CiState.Loading)

                state = awaitItem()

                assertTrue(state is CiState.Success)

                with(viewModel.title) {
                    clearText()
                    edit {
                        append("New Title")
                    }
                }

                viewModel.onAdd()

                awaitItem()
                awaitItem()

                val instruction = instructionRepository.getOne(2).first()
                assertEquals(instruction?.title, "New Title")
                assertEquals(instruction?.content?.size, state.content.size)
                assertEquals(instruction?.id, 2)
                assertEquals(instruction?.examId, 2)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun init_new() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CiViewModel(1, -1, instructionRepository, settingRepository)

        viewModel
            .ciState
            .test {
                var state = awaitItem()

                assertTrue(state is CiState.Loading)

                state = awaitItem()

                assertTrue(state is CiState.Success)

                assertEquals(viewModel.title.text.toString(), "")
                assertEquals(state.content.size, 1)
                assertEquals(state.id, -1)
                assertEquals(state.examId, 1)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNew() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CiViewModel(2, -1, instructionRepository, settingRepository)

        viewModel
            .ciState
            .test {
                var state = awaitItem()

                assertTrue(state is CiState.Loading)

                state = awaitItem()

                assertTrue(state is CiState.Success)

                with(viewModel.title) {
                    clearText()
                    edit {
                        append("New Title")
                    }
                }
                viewModel.onAdd()

                awaitItem()
                awaitItem()

                val instruction = instructionRepository
                    .getAllByExamId(examId = 2)
                    .first()
                    .last()
                assertEquals("New Title", instruction.title)
                assertEquals(instruction.content.size, state.content.size)
                assertEquals(instruction.examId, 2)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
