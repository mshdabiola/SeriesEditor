/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CeViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }

    // private val savedStateHandle = SavedStateHandle(mapOf(DETAIL_ID_ARG to 4))
    //  private lateinit var viewModel: ComposeExaminationViewModel
    private val subjectRepository by inject<ISubjectRepository>()
    private val examinationRepository by inject<IExaminationRepository>()

    @BeforeTest
    fun setup() = runTest(mainDispatcherRule.testDispatcher) {
        insertData()
        // viewModel = ComposeExaminationViewModel(0, subjectRepository, examinationRepository)
    }

    @Test
    fun updateExam_InitExam() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = ComposeExaminationViewModel(1, subjectRepository, examinationRepository)

        val state = viewModel.ceState
        assert(state.value is CeState.Loading)
        val exam = defaultData.examinations[0]

        state
            .test(timeout = (10L).toDuration(DurationUnit.SECONDS)) {
                awaitItem()
                val st = awaitItem()

                assertTrue((st as CeState.Success).isUpdate)

                assertEquals(viewModel.year.text.toString().toLong(), exam.year)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun updateExam() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = ComposeExaminationViewModel(1, subjectRepository, examinationRepository)

        val state = viewModel.ceState
        assert(state.value is CeState.Loading)
        state
            .test() {
                awaitItem()
                val st = awaitItem()

                assertTrue((st as CeState.Success).isUpdate)
                viewModel.year.clearText()
                viewModel.year.edit {
                    append("4555")
                }

                viewModel.addExam()
                awaitItem()
                awaitItem()

                val exam = examinationRepository.getOne(1).first()

                assertEquals(exam?.examination?.year, 4555)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun enterNewExam() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = ComposeExaminationViewModel(-1, subjectRepository, examinationRepository)
        viewModel
            .ceState
            .test() {
                awaitItem()
                val st = awaitItem()

                assertFalse((st as CeState.Success).isUpdate)

                assertEquals(viewModel.year.text.toString().toLong(), 2015)

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun addNewExam() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = ComposeExaminationViewModel(-1, subjectRepository, examinationRepository)
        viewModel
            .ceState
            .test() {
                awaitItem()
                val st = awaitItem()

                assertFalse((st as CeState.Success).isUpdate)
                viewModel.year.clearText()
                viewModel.year.edit {
                    append("4555")
                }
                viewModel.duration.clearText()
                viewModel.duration.edit {
                    append("56")
                }

                viewModel.addExam()
                awaitItem()
                awaitItem()

                val exam = examinationRepository.getAll().first()

                assertEquals(exam.last().year, 4555)

                cancelAndIgnoreRemainingEvents()
            }
    }
}
