/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import app.cash.turbine.test
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.testing.di.dataTestModule
import com.mshdabiola.testing.insertData
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
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
    private lateinit var viewModel: ComposeExaminationViewModel

    @BeforeTest
    fun setup() {
        val subjectRepository by inject<ISubjectRepository>()
        val examinationRepository by inject<IExaminationRepository>()
        viewModel = ComposeExaminationViewModel(0, subjectRepository, examinationRepository)
    }

    @Test
    fun allSubject() = runTest {
        insertData()
        viewModel.subjects
            .test {
                var item = awaitItem()
                println(item)

                item = awaitItem()
                println(item)
                this.cancelAndIgnoreRemainingEvents()
            }
    }
}
