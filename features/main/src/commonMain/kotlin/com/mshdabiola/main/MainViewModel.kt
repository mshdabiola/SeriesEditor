/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
) : ViewModel() {

    val classState = TextFieldState()


    private val _mainState = MutableStateFlow<MainState>(MainState.Loading())
    val mainState = _mainState.asStateFlow()


    fun addSubject() {

    }

}
