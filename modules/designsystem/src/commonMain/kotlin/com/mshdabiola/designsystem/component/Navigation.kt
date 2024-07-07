package com.mshdabiola.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun SeNavigationDrawerItem(
    selected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String,
) {
    NavigationDrawerItem(
        shape = RoundedCornerShape(4.dp),
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
