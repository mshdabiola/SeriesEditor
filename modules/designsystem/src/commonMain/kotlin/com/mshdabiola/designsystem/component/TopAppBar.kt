/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerMainTopAppBar(
    modifier: Modifier = Modifier,
    titleRes: String,
    onProfile: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(titleRes) },
        navigationIcon = {
            IconButton(onClick = onProfile) {
                Icon(Icons.Outlined.Person, "profile")
            }
        },

        actions = {
            IconButton(onClick = onNavigationClick) {
                Icon(Icons.Outlined.Settings, "setting")
            }

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerSubTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    modifier = Modifier.testTag("back"),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                )
            }

        },
        modifier = modifier.testTag("SerSubTopbar"),
    )
}
