/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SerMainTopAppBar
import com.mshdabiola.designsystem.component.SerSubTopAppBar

@Preview("Top App Bar")
@Composable
private fun SkTopAppBarPreview() {
    SerMainTopAppBar(
        titleRes = "Main Top Bar",

        )
}

@Preview("Top App Bar")
@Composable
private fun DetailTopAppBarPreview() {
    SerSubTopAppBar(
        title = "Detail Top Bar",
    )
}
