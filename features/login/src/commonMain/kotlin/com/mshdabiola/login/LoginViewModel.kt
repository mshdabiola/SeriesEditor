/*
 *abiola 2022
 */

package com.mshdabiola.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.seriesmodel.UserType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: IUserRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val username = TextFieldState("")
    val password = TextFieldState("")

    fun login() {
        viewModelScope.launch {
            val name = username.text.toString()
            val password = password.text.toString()

            val user = userRepository.getUserByPassword(name, password).first()
            if (user != null) {
                userDataRepository.setUserId(user.id)
            } else {
                val id = userRepository.setUser(
                    User(
                        name = name,
                        password = password,
                        type = UserType.TEACHER,
                        imagePath = "",
                        points = 5,
                    ),
                )

                userDataRepository.setUserId(id)
            }
        }
    }
}
