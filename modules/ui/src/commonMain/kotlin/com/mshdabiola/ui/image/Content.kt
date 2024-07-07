package com.mshdabiola.ui.image

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MoveDown
import androidx.compose.material.icons.filled.MoveUp
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.model.ImageUtil
import com.mshdabiola.retex.Latex
import com.mshdabiola.retex.MarkUpText
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    color: Color = ListItemDefaults.containerColor,
    items: ImmutableList<ItemUiState>,
    examId: Long,
) {
    Column(modifier) {
        items.forEachIndexed { _, item ->

            ListItem(
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                colors = ListItemDefaults.colors(containerColor = color),
                headlineContent = {
                    val childModifier = Modifier.fillMaxWidth()

                    when (item.type) {
                        Type.EQUATION -> Box(childModifier, contentAlignment = Alignment.Center) {
                            Latex(modifier = Modifier, item.content.text.toString())
                        }

                        Type.TEXT -> MarkUpText(
                            modifier = childModifier,
                            text = item.content.text.toString(),
                        )

                        Type.IMAGE ->
                            Box(childModifier, contentAlignment = Alignment.Center) {
                                ImageUi(
                                    Modifier.fillMaxWidth().aspectRatio(16f / 9f),
                                    path = ImageUtil.getAppPath(item.content.text.toString()).path,
                                    contentDescription = "",
                                )
                            }
                    }
                },

                )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    items: ImmutableList<ItemUiState>,
    label: String,
    addUp: (Int) -> Unit = {},
    addBottom: (Int) -> Unit = {},
    delete: (Int) -> Unit = {},
    moveUp: (Int) -> Unit = {},
    moveDown: (Int) -> Unit = {},
    changeView: (Int) -> Unit = {},
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onItemClicked: (ItemUiState) -> Unit = {},


    ) {
    Column(modifier) {
        items.forEachIndexed { index, item ->
            var showContext by remember { mutableStateOf(false) }
            var showChange by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val childModifier = Modifier.weight(1f).testTag("content")

                when (item.type) {
                    Type.EQUATION -> {
                        if (item.content.text.isBlank()) {
                            Box(childModifier, contentAlignment = Alignment.Center) {
                                TextButton(onClick = { onItemClicked(item) }) {
                                    Icon(Icons.Default.Add, "add")
                                    Text("Add Equation")
                                }
                            }

                        } else {
                            EquationContent(
                                childModifier
                                    .clickable { onItemClicked(item) },
                                item,
                            )
                        }
                    }

                    Type.TEXT -> TextContent(childModifier, item, "$label line ${index + 1}")

                    Type.IMAGE -> {
                        if (item.content.text.isBlank()) {
                            Box(childModifier, contentAlignment = Alignment.Center) {
                                TextButton(onClick = { onItemClicked(item) }) {
                                    Icon(Icons.Default.Add, "add")
                                    Text("Add Image")
                                }
                            }

                        } else {
                            ImageContent(
                                childModifier
                                    .clickable { onItemClicked(item) }
                                    .aspectRatio(16f / 9f),
                                item,
                            )
                        }

                    }
                }
                Box {
                    IconButton(onClick = { showContext = true }) {
                        Icon(Icons.Default.MoreVert, "")
                    }

                    DropdownMenu(
                        expanded = showContext,
                        onDismissRequest = {
                            showContext = false
                            showChange = false
                        },
                    ) {
                        if (showChange) {
                            if (item.type != Type.IMAGE) {
                                DropdownMenuItem(
                                    text = { Text("Image") },
                                    onClick = {
                                        changeType(index, Type.IMAGE)
                                        showChange = false
                                        showContext = false
                                    },
                                )
                            }

                            if (item.type != Type.EQUATION) {
                                DropdownMenuItem(
                                    text = { Text("Equation") },
                                    onClick = {
                                        changeType(index, Type.EQUATION)
                                        showChange = false
                                        showContext = false
                                    },
                                )
                            }

                            if (item.type != Type.TEXT) {
                                DropdownMenuItem(
                                    text = { Text("Text") },
                                    onClick = {
                                        changeType(index, Type.TEXT)
                                        showChange = false
                                        showContext = false
                                    },
                                )
                            }
                        } else {
                            DropdownMenuItem(
                                leadingIcon = {
                                    if (!item.isEditMode) {
                                        Icon(
                                            Icons.Default.Edit,
                                            "edit",
                                        )
                                    } else {
                                        Icon(Icons.Default.ViewAgenda, "view")
                                    }
                                },
                                text = { Text(if (!item.isEditMode) "Edit" else "View") },
                                onClick = {
                                    changeView(index)
                                    showContext = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(Icons.Default.ChangeCircle, "change")
                                },
                                text = { Text("Change Type") },
                                onClick = {
                                    showChange = true
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                text = { Text("Add Top") },
                                onClick = {
                                    addUp(index)
                                    showContext = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                text = { Text("Add Down") },
                                onClick = {
                                    addBottom(index)
                                    showContext = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Delete, "delete") },
                                text = { Text("Delete") },
                                onClick = {
                                    delete(index)
                                    showContext = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.MoveUp, "delete") },
                                text = { Text("Move up") },
                                onClick = {
                                    moveUp(index)
                                    showContext = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.MoveDown, "delete") },
                                text = { Text("Move down") },
                                onClick = {
                                    moveDown(index)
                                    showContext = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EquationContent(
    modifier: Modifier = Modifier,
    equation: ItemUiState,
) {
    key(equation.content.text){
        Latex(modifier = modifier, equation.content.text.toString())
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    image: ItemUiState,
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        //  if (File(path).exists()) {
        ImageUi(
            Modifier,
            path = ImageUtil.getAppPath(image.content.text.toString()).path,
            contentDescription = "",
        )

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextContent(
    modifier: Modifier = Modifier,
    text: ItemUiState,
    label: String,
) {
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(text.focus) {
        if (text.focus) {
            focusRequester.requestFocus()
        }
    }
    if (text.isEditMode) {
        SeriesEditorTextField(
            modifier = modifier.focusRequester(focusRequester),
            label = label,
            maxNum = androidx.compose.foundation.text2.input.TextFieldLineLimits.SingleLine,
            state = text.content,
            imeAction = ImeAction.Next,
        )


    } else {
        MarkUpText(modifier = modifier, text = text.content.text.toString())
    }
}
