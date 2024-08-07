/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.exportableData
import com.mshdabiola.testing.questions
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.delay
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

class CqViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val questionRepository by inject<IQuestionRepository>()
    private val settingRepository by inject<ISettingRepository>()
    private val instructionRepository by inject<IInstructionRepository>()
    private val examinationRepository by inject<IExaminationRepository>()
    private val topicategoryRepository by inject<ITopicCategory>()

    @Test
    fun init_update() = runTest(mainDispatcherRule.testDispatcher) {
        val question = questions[0]

        val viewModel = CqViewModel(
            question.examId,
            question.id,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )

        viewModel
            .cqState
            .test {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                assertEquals(question.id, state.questionUiState.id)
                assertEquals(question.number, state.questionUiState.number)
//                assertEquals(question.title,state.questionUiState.title)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun update() = runTest(mainDispatcherRule.testDispatcher) {
        val default = exportableData
        val question = default.questions[0]

        val viewModel = CqViewModel(
            question.examId,
            question.id,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )

        viewModel
            .cqState
            .test {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                with(state.questionUiState.contents[0].content) {
                    clearText()
                    edit {
                        append("What is ur name")
                    }
                }

//
                viewModel.onAddQuestion()
                state = awaitItem()
                state = awaitItem()

                var questionNew = questionRepository.getOne(question.id).first()
                assertEquals(question.id, questionNew?.id)
                assertEquals("What is ur name", questionNew?.contents?.get(0)?.content)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun init_new() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CqViewModel(
            1,
            -1,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )

        viewModel
            .cqState
            .test {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                with(state.questionUiState) {
                    assertEquals("", contents[0].content.text)
                    assertEquals(1, contents.size)
                    assertEquals(id, -1)
                    assertEquals(examId, 1)
                }

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNew() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = CqViewModel(
            2,
            -1,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )

        viewModel
            .cqState
            .test {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                with(state.questionUiState.contents[0].content) {
                    clearText()
                    edit {
                        append("What is ur name")
                    }
                }

//
                viewModel.onAddQuestion()
                state = awaitItem()
                state = awaitItem()

                var questionNew = questionRepository.getByExamId(2).first().last()
                assertEquals("What is ur name", questionNew.contents[0].content)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNewInputQuestionTest() = runTest(mainDispatcherRule.testDispatcher) {
        val text = """
             *q* What is your name?
               *o* John
               *o* Jane
               *o* Joe
               *o* Jack
               *a* John
              *q* What is your age?
               *o* 10
               *o* 20
               *o* 30
               *o* 40
               *a* 20
        """.trimIndent()

        val viewModel = CqViewModel(
            2,
            -1,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )

        viewModel
            .cqState
            .test {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                state = awaitItem()

                assertTrue(state is CqState.Success)

                with(viewModel.inputQuestions) {
                    edit {
                        append(text)
                    }
                }
                delay(3000)

//
                viewModel.onAddQuestionsFromInput()
                state = awaitItem()
                state = awaitItem()

                val questionNew = questionRepository.getByExamId(2).first()
                assertEquals(3, questionNew.size)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
