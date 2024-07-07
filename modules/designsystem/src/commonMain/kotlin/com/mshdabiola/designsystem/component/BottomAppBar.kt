package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SeButtonAppBar(
    modifier: Modifier = Modifier,
    onFabClick: (() -> Unit)? = null,
    onNavigationClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    BottomAppBar(
        modifier = modifier,
        floatingActionButton = {
            if (onFabClick != null) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.testTag("add"),
                    onClick = onFabClick,
                ) {
                    Icon(Icons.Default.Add, "add")
                    Spacer(Modifier.width(8.dp))
                    Text("Add")
                }

            }
        },
        actions = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(Icons.Default.Menu, "menu")
                }
            }
            if (onSettingsClick != null) {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, "setting")
                }
            }
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBackIosNew, "back")
                }

            }

        },
    )
}
