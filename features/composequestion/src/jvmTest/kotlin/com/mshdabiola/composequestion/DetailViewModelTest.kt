/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import app.cash.turbine.test
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.testing.databaseTestModule
import com.mshdabiola.testing.di.dataTestModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test

class DetailViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }


    // private val savedStateHandle = SavedStateHandle(mapOf(DETAIL_ID_ARG to 4))
    private lateinit var viewModel: CqViewModel

    @BeforeTest
    fun setup() {
        val questionRepository by inject<IQuestionRepository>()
        val settingRepository by inject<ISettingRepository>()
        val instructionRepository by inject<IInstructionRepository>()
        val subjectRepository by inject<ISubjectRepository>()
        val examinationRepository by inject<IExaminationRepository>()
        val topicategoryRepository by inject<ITopicCategory>()
        viewModel = CqViewModel(
            1,
            1,
            questionRepository,
            instructionRepository,
            examinationRepository,
            settingRepository,
            topicategoryRepository,
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        viewModel.topic.test {
            var item = awaitItem()
            println(item)

            cancelAndIgnoreRemainingEvents()

        }
    }
}
