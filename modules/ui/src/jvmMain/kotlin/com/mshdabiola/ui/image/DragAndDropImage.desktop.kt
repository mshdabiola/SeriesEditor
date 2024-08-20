package com.mshdabiola.ui.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.File

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
actual fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    isEmpty: Boolean,
    onPathChange: (String) -> Unit,
) {
    var isover by remember { mutableStateOf(false) }
    val dragAndDropTarget = remember {
        object : DragAndDropTarget {

            override fun onStarted(event: DragAndDropEvent) {
                isover = true
            }

            override fun onEnded(event: DragAndDropEvent) {
                isover = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                val transferable = event.awtTransferable

                val file = transferable.transferDataFlavors
                    .filter {
                        it.isFlavorJavaFileListType
                    }
                    .mapNotNull {
                        transferable.getTransferData(it) as? List<*>
                    }
                    .flatten()
                    .firstNotNullOf {
                        it as? File
                    }
                onPathChange(file.path)
                return true
            }
        }
    }

    Card(
        modifier = modifier
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true },
                target = dragAndDropTarget,
            ),
        border = if (isover) {
            BorderStroke(
                width = 4.dp,
                color = Color.Green,
            )
        } else {
            BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
        },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (!isEmpty) {
                ImageUi(
                    Modifier,
                    path = path,
                    contentDescription = "",
                )
            } else {
                Text(text = "drag image here")
            }
        }
    }
}

// @OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
// @Composable
// fun MainForm() {
//    val exportedText = "Hello, DnD!"
//    Row(
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxSize(),
//    ) {
//        val textMeasurer = rememberTextMeasurer()
//        Box(
//            Modifier
//                .size(200.dp)
//                .background(Color.LightGray)
//                .dragAndDropSource(
//                    drawDragDecoration = {
//                        drawRect(
//                            color = Color.White,
//                            topLeft = Offset(x = 0f, y = size.height / 4),
//                            size = Size(size.width, size.height / 2),
//                        )
//                        val textLayoutResult = textMeasurer.measure(
//                            text = AnnotatedString(exportedText),
//                            layoutDirection = layoutDirection,
//                            density = this,
//                        )
//                        drawText(
//                            textLayoutResult = textLayoutResult,
//                            topLeft = Offset(
//                                x = (size.width - textLayoutResult.size.width) / 2,
//                                y = (size.height - textLayoutResult.size.height) / 2,
//                            ),
//                        )
//                    },
//                ) {
//                    detectDragGestures(
//                        onDragStart = { offset ->
//                            startTransfer(
//                                DragAndDropTransferData(
//                                    transferable = DragAndDropTransferable(
//                                        StringSelection(exportedText),
//                                    ),
//                                    supportedActions = listOf(
//                                        DragAndDropTransferAction.Copy,
//                                        DragAndDropTransferAction.Move,
//                                        DragAndDropTransferAction.Link,
//                                    ),
// //                                initialAction = DragAndDropTransferAction.Copy,
// //                                dragOffset = offset,
//                                    onTransferCompleted = { action ->
//                                        when (action) {
//                                            null -> println("Transfer aborted")
//                                            DragAndDropTransferAction.Copy -> println("Copied")
//                                            DragAndDropTransferAction.Move -> println("Moved")
//                                            DragAndDropTransferAction.Link -> println("Linked")
//                                        }
//                                    },
//                                ),
//                            )
//                        },
//                        onDrag = { _, _ -> },
//                    )
//                },
//        ) {
//            Text("Drag Me", Modifier.align(Alignment.Center))
//        }
//
//        var showTargetBorder by remember { mutableStateOf(false) }
//        var targetText by remember { mutableStateOf("Drop Here") }
//        val coroutineScope = rememberCoroutineScope()
//        val dragAndDropTarget = remember {
//            object : DragAndDropTarget {
//
//                override fun onStarted(event: DragAndDropEvent) {
//                    showTargetBorder = true
//                }
//
//                override fun onEnded(event: DragAndDropEvent) {
//                    showTargetBorder = false
//                }
//
//                override fun onDrop(event: DragAndDropEvent): Boolean {
//                    val result = targetText == "Drop Here"
//                    targetText = event.awtTransferable.let {
//                        if (it.isDataFlavorSupported(DataFlavor.stringFlavor))
//                            it.getTransferData(DataFlavor.stringFlavor) as String
//                        else
//                            it.transferDataFlavors.first().humanPresentableName
//                    }
//                    coroutineScope.launch {
//                        delay(2000)
//                        targetText = "Drop Here"
//                    }
//                    return result
//                }
//            }
//        }
//        Box(
//            Modifier
//                .size(200.dp)
//                .background(Color.LightGray)
//                .then(
//                    if (showTargetBorder)
//                        Modifier.border(BorderStroke(3.dp, Color.Black))
//                    else
//                        Modifier,
//                )
//                .dragAndDropTarget(
//                    shouldStartDragAndDrop = { true },
//                    target = dragAndDropTarget,
//                ),
//        ) {
//            Text(targetText, Modifier.align(Alignment.Center))
//        }
//    }
// }
