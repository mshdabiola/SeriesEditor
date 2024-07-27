/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import co.touchlab.kermit.Logger
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.testing.defaultData
import com.mshdabiola.testing.di.dataTestModule
import com.mshdabiola.testing.insertData
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CtViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val topicRepository by inject<ITopicRepository>()
    private val categoryRepository by inject<ITopicCategory>()

    @BeforeTest
    fun setup() = runTest(testDispatcher) {
        insertData()
    }

    @Test
    fun init_update() = runTest(testDispatcher) {
        val default = defaultData
        val topic = default.topics[4]

        val topicCategory = default.topicCategory.single { it.id == topic.categoryId }

        val viewModel = CtViewModel(
            topicCategory.subjectId,
            topic.id,
            topicRepository,
            categoryRepository,
            Logger,

        )

        viewModel
            .ctState
            .test {
                var state = awaitItem()

                assertTrue(state is CtState.Loading)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                assertEquals(viewModel.state.text, topic.title)
                assertEquals(topic.id, state.topicId)

                assertEquals(viewModel.categoryState.text.toString(), topicCategory.name)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun update() = runTest(testDispatcher) {
        val default = defaultData
        val topic = default.topics[0]

        val topicCategory = default.topicCategory.single { it.id == topic.categoryId }

        val viewModel = CtViewModel(
            topicCategory.subjectId,
            topic.id,
            topicRepository,
            categoryRepository,
            Logger,

        )

        viewModel
            .ctState
            .test {
                var state = awaitItem()

                assertTrue(state is CtState.Loading)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                with(viewModel.state) {
                    clearText()
                    edit {
                        append("Physics")
                    }
                }
                viewModel.addTopic()

                awaitItem()
                awaitItem()

                val newTopic = topicRepository.getOne(topic.id).first()

                assertEquals("Physics", newTopic?.title)
                assertEquals(topic.id, newTopic?.id)
                assertEquals(topicCategory.id, newTopic?.categoryId)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun init_new() = runTest(testDispatcher) {
        val default = defaultData
        val topic = default.topics[4]

        val topicCategory = default.topicCategory.single { it.id == topic.categoryId }

        val viewModel = CtViewModel(
            topicCategory.subjectId,
            -1,
            topicRepository,
            categoryRepository,
            Logger,

        )

        viewModel
            .ctState
            .test {
                var state = awaitItem()

                assertTrue(state is CtState.Loading)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                assertEquals("", viewModel.state.text)
                assertEquals(-1, state.topicId)

                assertEquals("", viewModel.categoryState.text.toString())

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNew() = runTest(testDispatcher) {
        val default = defaultData
        val topic = default.topics[0]

        val topicCategory = default.topicCategory.single { it.id == topic.categoryId }

        val viewModel = CtViewModel(
            topicCategory.subjectId,
            -1,
            topicRepository,
            categoryRepository,
            Logger,

        )

        viewModel
            .ctState
            .test {
                var state = awaitItem()

                assertTrue(state is CtState.Loading)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                state = awaitItem()

                assertTrue(state is CtState.Success)

                with(viewModel.state) {
                    clearText()
                    edit {
                        append("New Subject")
                    }
                }
                viewModel.addTopic()

                awaitItem()
                awaitItem()

                val newTopic = topicRepository.getAllBySubject(topicCategory.subjectId).first().last()

                assertEquals("New Subject", newTopic.title)
                assertEquals(topicCategory.id, newTopic.categoryId)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
