/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction

import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.ISettingRepository
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
    private lateinit var viewModel: CiViewModel

    @BeforeTest
    fun setup() {
        val instructionRepository by inject<IInstructionRepository>()
        val settingRepository by inject<ISettingRepository>()
        viewModel = CiViewModel(0, 0, instructionRepository, settingRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
    }
}
