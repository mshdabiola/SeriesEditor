/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.UserRepository
import com.mshdabiola.data.repository.toWord
import com.mshdabiola.model.Platform
import com.mshdabiola.model.UserData
import com.mshdabiola.model.currentPlatform
import com.mshdabiola.serieseditor.MainActivityUiState.Loading
import com.mshdabiola.serieseditor.MainActivityUiState.Success
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.seriesmodel.UserType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainAppViewModel(
    userDataRepository: UserDataRepository,
    userRepository: UserRepository,
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Success())
    val mainState = _mainState.asStateFlow()
    private var user: User? = null

    init {

        viewModelScope.launch {

            user = userRepository.getUser(1).first()

            if (user == null) {
                user = User(
                    id = -1,
                    name = "Abiola",
                    type = UserType.TEACHER,
                    password = "cheatmobi",
                    imagePath = "",
                    points = 1,
                )

                val id = userRepository.setUser(user!!)
                userDataRepository.setUserId(id)
            }
        }
    }

    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
