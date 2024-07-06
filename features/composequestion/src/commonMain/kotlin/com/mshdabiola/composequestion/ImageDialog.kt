package com.mshdabiola.composequestion

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.component.MainExportDialog
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.ui.image.DragAndDropImage
import com.mshdabiola.ui.state.ItemUiState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QuestionDialog(
    modifier: Modifier = Modifier,
    itemUiState: ItemUiState?,
    onDismiss: () -> Unit = {},
) {

    if (itemUiState != null) {
        when(itemUiState.type){
            Type.EQUATION->{}
            Type.TEXT->{}
            Type.IMAGE->{
                ImageDialog(textFieldState = itemUiState.content, onDismiss = onDismiss)
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDialog(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onDismiss: () -> Unit={}
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            if(textFieldState.text.isNotBlank()){
                TextButton(onClick = {textFieldState.clearText()}) {
                    Text("Remove Image")
                }
            }

        },
        confirmButton = {
            SeriesEditorButton(
                onClick = onDismiss,
                ) {
                Text(if(textFieldState.text.isNotBlank()) "Close " else "Add Image")
            }
        },
        text = {
                Box(modifier.aspectRatio(16f / 9f), contentAlignment = Alignment.Center) {
                    DragAndDropImage(
                        modifier = modifier.fillMaxSize(),
                        path = textFieldState.text.toString(),
                        onPathChange = {path->
                            textFieldState.clearText()

                            textFieldState.edit {
                                append(path)
                            }
                        },
                    )
                }
        },
    )
}