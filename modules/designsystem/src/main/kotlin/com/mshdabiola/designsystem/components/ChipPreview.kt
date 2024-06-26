/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SkFilterChip
import com.mshdabiola.designsystem.theme.SeriesEditorTheme

@ThemePreviews
@Composable
fun ChipPreview() {
    SeriesEditorTheme {
        SeriesEditorBackground(modifier = Modifier.size(80.dp, 20.dp)) {
            SkFilterChip(selected = true, onSelectedChange = {}) {
                Text("Chip")
            }
        }
    }
}
