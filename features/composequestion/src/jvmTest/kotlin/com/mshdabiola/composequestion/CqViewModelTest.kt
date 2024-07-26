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
import com.mshdabiola.testing.defaultData
import com.mshdabiola.testing.di.dataTestModule
import com.mshdabiola.testing.insertData
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
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
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CqViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val questionRepository by inject<IQuestionRepository>()
    private val settingRepository by inject<ISettingRepository>()
    private val instructionRepository by inject<IInstructionRepository>()
    private val examinationRepository by inject<IExaminationRepository>()
    private val topicategoryRepository by inject<ITopicCategory>()

    @BeforeTest
    fun setup() = runTest {
        insertData()
        defaultData
            .questions


    }

    @Test
    fun init_update() = runTest {
        val default = defaultData
        val question=default.questions[0]

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
            .test((10).toDuration(DurationUnit.SECONDS)) {
                var state = awaitItem()

                assertTrue(state is CqState.Loading)

                state = awaitItem()

                assertTrue(state is CqState.Success)



                assertEquals(question.id,state.questionUiState.id)
                assertEquals(question.number,state.questionUiState.number)
//                assertEquals(question.title,state.questionUiState.title)
                assertEquals(question.topicId,state.questionUiState.topicUiState?.id)
                assertEquals(question.instructionId,state.questionUiState.instructionUiState?.id)

                cancelAndIgnoreRemainingEvents()

            }


    }

    @Test
    fun update() = runTest {
        val default = defaultData
        val question=default.questions[0]

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
                assertEquals(question.id,questionNew?.id)
                assertEquals("What is ur name", questionNew?.contents?.get(0)?.content)


                cancelAndIgnoreRemainingEvents()


            }


    }


    @Test
    fun init_new() = runTest {
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


                with(state.questionUiState){
                    assertEquals("",contents[0].content.text)
                    assertEquals(1,contents.size)
                    assertEquals(id, -1)
                    assertEquals(examId, 1)
                }


                cancelAndIgnoreRemainingEvents()


            }


    }

    @Test
    fun addNew() = runTest {

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
}
