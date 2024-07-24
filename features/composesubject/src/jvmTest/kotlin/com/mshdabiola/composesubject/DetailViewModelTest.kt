/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import co.touchlab.kermit.Logger
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.di.dataTestModule
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
    private lateinit var viewModel: ComposeSubjectViewModel

    @BeforeTest
    fun setup() {
        val seriesRepository by inject<ISeriesRepository>()
        val userdataRepository by inject<UserDataRepository>()
        val subjectRepository by inject<ISubjectRepository>()
        val logger = Logger
        viewModel = ComposeSubjectViewModel(
            0,
            seriesRepository,
            subjectRepository,
            userdataRepository,
            logger,

        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
    }
}
