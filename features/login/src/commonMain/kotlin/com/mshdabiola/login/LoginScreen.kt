/*
 *abiola 2022
 */

package com.mshdabiola.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
) {
    val viewModel: LoginViewModel = koinViewModel()

    LoginScreen(
        modifier = modifier,
        user = viewModel.username,
        password = viewModel.password,
        login = viewModel::login,

    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    user: TextFieldState,
    password: TextFieldState,
    login: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SeriesEditorTextField(
            modifier = Modifier.testTag("login:user"),
            state = user,
            label = "Username",
        )
        SeriesEditorTextField(
            modifier = Modifier.testTag("login:password"),
            state = password,
            label = "Password",
        )
        SeriesEditorButton(
            modifier = Modifier.testTag("login:button"),
            onClick = login,
            enabled = user.text.isNotBlank() && password.text.isNotBlank(),
        ) {
            Text("Login")
        }
    }
}
