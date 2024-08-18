/*
 *abiola 2022
 */

package com.mshdabiola.main

import app.cash.turbine.test
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals

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
    private val subjectRepository by inject<ISubjectRepository>()
    private val examRepository by inject<IExaminationRepository>()
    private val questionRepository by inject<IQuestionRepository>()
    private val userdataRepository by inject<IUserRepository>()

    @Test
    fun init() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            seriesRepository,
            subjectRepository,
            examRepository,
            questionRepository,
            userdataRepository,
        )

        viewModel
            .mainState
            .test {
                var state = awaitItem()

                assertEquals(0, state.series.size)

                state = awaitItem()

                assertEquals(10, state.series.size)
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun delete() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            seriesRepository,
            subjectRepository,
            examRepository,
            questionRepository,
            userdataRepository,
        )

        viewModel
            .mainState
            .test {
                var state = awaitItem()

                assertEquals(0, state.series.size)

                awaitItem()

                viewModel.deleteClass(1)

                state = awaitItem()

                assertEquals(9, state.series.size)
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun update() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            seriesRepository,
            subjectRepository,
            examRepository,
            questionRepository,
            userdataRepository,
        )

        viewModel
            .mainState
            .test {
                var state = awaitItem()

                assertEquals(0, state.series.size)

                awaitItem()
                val series = seriesRepository.getOne(1).first()!!

                viewModel.updateClass(series.id)
                delay(2000)
                assertEquals(series.name, viewModel.classState.text.toString())

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun add() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = MainViewModel(
            seriesRepository,
            subjectRepository,
            examRepository,
            questionRepository,
            userdataRepository,
        )

        viewModel
            .mainState
            .test {
                var state = awaitItem()

                assertEquals(0, state.series.size)

                awaitItem()

                viewModel.classState.edit {
                    append("Moshood")
                }
                viewModel.addClass()

                state = awaitItem()
                assertEquals("Moshood", state.series.last().name)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
