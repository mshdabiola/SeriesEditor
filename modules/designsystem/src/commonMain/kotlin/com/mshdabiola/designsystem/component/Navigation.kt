package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun SeNavigationDrawerItem(
    selected: Boolean=false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String,
) {
    NavigationDrawerItem(
        selected = selected,
        onClick = onClick,
        icon = {
            if (icon != null) {
                Icon(imageVector = icon, label)
            }
        },
        modifier = modifier,
        label = { Text(label) },
        colors = NavigationDrawerItemDefaults.colors(),
    )
}
