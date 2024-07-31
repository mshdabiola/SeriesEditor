/*
 *abiola 2022
 */

package com.mshdabiola.topics

import app.cash.turbine.test
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
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

class TopicsViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    val topicRepository by inject<ITopicRepository>()
    val categoryRepository by inject<ITopicCategory>()

    @Test
    fun init() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = TopicsViewModel(
            1,
            categoryRepository,
            topicRepository,
        )

        viewModel
            .topics
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                assertEquals(
                    1,
                    state.data.size,

                )

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun delete() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = TopicsViewModel(
            1,
            categoryRepository,
            topicRepository,
        )

        viewModel
            .topics
            .test {
                var state = awaitItem()

                assertTrue(state is Result.Loading)

                state = awaitItem()

                assertTrue(state is Result.Success)

                val topic = state.data.first()
//
                viewModel.onDelete(topic.id)
//
                // state = awaitItem()
//
//                assertTrue(state is Result.Success)
//
//                assertEquals(
//                    9,
//                    state.data.size,
//
//                )
//
//                cancelAndIgnoreRemainingEvents()
            }
    }
}
