package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
fun ContentPreview() {
    Content(
        modifier = Modifier,
        items = listOf(ItemUiState()).toImmutableList(),
        addUp = {},
        addBottom = {},
        delete = {},
        moveUp = {},
    )
}
