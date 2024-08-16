/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SerMainTopAppBar
import com.mshdabiola.designsystem.component.SerSubTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun SkTopAppBarPreview() {
    SerMainTopAppBar(
        titleRes = "Main Top Bar",

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun DetailTopAppBarPreview() {
    SerSubTopAppBar(
        title = "Detail Top Bar",
    )
}
