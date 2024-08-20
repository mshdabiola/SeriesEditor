/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.UserData
import com.mshdabiola.serieseditor.MainActivityUiState.Loading
import com.mshdabiola.serieseditor.MainActivityUiState.Success
import com.mshdabiola.seriesmodel.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainAppViewModel(
    userDataRepository: UserDataRepository,
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState())
    val mainState = _mainState.asStateFlow()
    private var user: User? = null

    init {

        viewModelScope.launch {
            userDataRepository
                .userData
                .map { it.userId }
                .collectLatest { id ->
                    _mainState.update {
                        it.copy(userId = id)
                    }
                }

//            user = userRepository.getUser(1).first()
//
//            if (user == null) {
//                user = User(
//                    id = -1,
//                    name = "Abiola",
//                    type = UserType.TEACHER,
//                    password = "cheatmobi",
//                    imagePath = "",
//                    points = 1,
//                )
//
//                val id = userRepository.setUser(user!!)
//                userDataRepository.setUserId(id)
//            }
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
