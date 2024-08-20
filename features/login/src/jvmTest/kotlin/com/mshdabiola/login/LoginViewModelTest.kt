/*
 *abiola 2022
 */

package com.mshdabiola.main

import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.login.LoginViewModel
import com.mshdabiola.testing.dataTestModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals

class LoginViewModelTest : KoinTest {

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule(order = 3)
    val koinTestRule = KoinTestRule.create {
        this.modules(dataTestModule)
    }
    private val userRepository by inject<IUserRepository>()
    private val userdataRepository by inject<UserDataRepository>()

    @Test
    fun login() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = LoginViewModel(userRepository, userdataRepository)

        viewModel.username.edit {
            append("abiola")
        }

        viewModel.password.edit {
            append("123456")
        }
        delay(2000)

        viewModel.login()
        delay(2000)
        val user = userRepository.getUser(11).first()!!
        assertEquals("abiola", user.name)
    }
}
