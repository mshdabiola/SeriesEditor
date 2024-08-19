/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import com.mshdabiola.data.repository.ISubjectRepository
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

class CsViewModelTest : KoinTest {

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

    @Test
    fun init_update() = runTest(testDispatcher) {
        val default = exportableData
        val subject = default.subjects[0]

        val viewModel = ComposeSubjectViewModel(
            1,
            subject.id,
            subjectRepository,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                assertEquals(subject.seriesId, state.id)
                assertEquals(subject.title, viewModel.subjectState.text)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun update() = runTest(testDispatcher) {
        val default = exportableData
        val subject = default.subjects[2]

        val viewModel = ComposeSubjectViewModel(
            subject.seriesId,
            subject.id,
            subjectRepository,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                with(viewModel.subjectState) {
                    clearText()
                    edit {
                        append("English Language")
                    }
                }

                viewModel.addSubject()

                state = awaitItem()
                assertTrue(state is CsState.Loading)
                state = awaitItem()
                assertTrue(state is CsState.Loading)

                val newSubject = subjectRepository.getOneWithSeries(subject.id).first()

                assertEquals(subject.id, newSubject?.subject?.id)
                assertEquals("English Language", newSubject?.subject?.title)
                assertEquals(subject.seriesId, newSubject?.series?.id)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun init_new() = runTest(testDispatcher) {
        val viewModel = ComposeSubjectViewModel(
            1,
            -1,
            subjectRepository,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                assertEquals(1, state.id)
                assertEquals("", viewModel.subjectState.text)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNew() = runTest(testDispatcher) {
        val viewModel = ComposeSubjectViewModel(
            1,
            -1,
            subjectRepository,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                with(viewModel.subjectState) {
                    clearText()
                    edit {
                        append("Physics")
                    }
                }

                viewModel.addSubject()

                state = awaitItem()
                assertTrue(state is CsState.Loading)
                state = awaitItem()
                assertTrue(state is CsState.Loading)

                val newSubject = subjectRepository.getAll().first().last()

                assertEquals("Physics", newSubject.title)
                assertEquals(1, newSubject.seriesId)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
