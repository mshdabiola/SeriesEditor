/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.designsystem.theme.SeriesEditorTheme

@ThemePreviews
@Composable
fun ButtonPreview() {
    SeriesEditorTheme {
        SeriesEditorBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesEditorButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonPreview2() {
    SeriesEditorTheme {
        SeriesEditorBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesEditorButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonLeadingIconPreview() {
    SeriesEditorTheme {
        SeriesEditorBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesEditorButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = SkIcons.Add, contentDescription = null) },
            )
        }
    }
}
