/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import co.touchlab.kermit.Logger
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.defaultData
import com.mshdabiola.testing.di.dataTestModule
import com.mshdabiola.testing.insertData
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.selects.whileSelect
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    private val seriesRepository by inject<ISeriesRepository>()
    private val userdataRepository by inject<UserDataRepository>()
    private val subjectRepository by inject<ISubjectRepository>()
    private val logger = Logger


    @BeforeTest
    fun setup() =runTest(testDispatcher)  {
        insertData()
    }

    @Test
    fun init_update() = runTest(testDispatcher)  {
        val default = defaultData
        val subject=default.subjects[0]



        val  viewModel = ComposeSubjectViewModel(
            subject.id,
            seriesRepository,
            subjectRepository,
            userdataRepository,
            logger,
            )

        viewModel
            .csState
            .test((10).toDuration(DurationUnit.SECONDS)) {


                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                assertEquals(subject.seriesId,state.currentSeries)
                assertEquals(subject.title,viewModel.subjectState.text)

                cancelAndIgnoreRemainingEvents()

            }


    }

    @Test
    fun update() =runTest(testDispatcher) {
        val default = defaultData
        val subject=default.subjects[2]


        val  viewModel = ComposeSubjectViewModel(
            subject.id,
            seriesRepository,
            subjectRepository,
            userdataRepository,
            logger,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                with(viewModel.subjectState){
                    clearText()
                    edit {
                        append("English Language")
                    }
                }

                viewModel.addSubject()


                state=awaitItem()
                assertTrue(state is CsState.Loading)
                state=awaitItem()
                assertTrue(state is CsState.Loading)

                val newSubject=subjectRepository.getOneWithSeries(subject.id).first()


                assertEquals(subject.id,newSubject?.subject?.id)
                assertEquals("English Language",newSubject?.subject?.title)
                assertEquals(subject.seriesId,newSubject?.series?.id)

                cancelAndIgnoreRemainingEvents()


            }


    }


    @Test
    fun init_new() = runTest(testDispatcher)  {
        val viewModel = ComposeSubjectViewModel(
            -1,
            seriesRepository,
            subjectRepository,
            userdataRepository,
            logger,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                assertEquals(1,state.currentSeries)
                assertEquals("",viewModel.subjectState.text)

                cancelAndIgnoreRemainingEvents()


            }


    }

    @Test
    fun addNew() = runTest(testDispatcher) {

        val viewModel = ComposeSubjectViewModel(
           -1,
            seriesRepository,
            subjectRepository,
            userdataRepository,
            logger,
        )

        viewModel
            .csState
            .test {
                var state = awaitItem()

                assertTrue(state is CsState.Loading)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                state = awaitItem()

                assertTrue(state is CsState.Success)

                with(viewModel.subjectState){
                    clearText()
                    edit {
                        append("Physics")
                    }
                }

                viewModel.addSubject()


                state=awaitItem()
                assertTrue(state is CsState.Loading)
                state=awaitItem()
                assertTrue(state is CsState.Loading)

                val newSubject=subjectRepository.getAll().first().last()


                assertEquals("Physics", newSubject.title)
                assertEquals(1, newSubject.seriesId)

                cancelAndIgnoreRemainingEvents()

            }


    }
}
