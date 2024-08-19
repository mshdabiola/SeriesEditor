package com.mshdabiola.subjects

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mshdabiola.seriesmodel.SubjectWithSeries

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectWithSeries: SubjectWithSeries,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    onClick: (Long) -> Unit = {},
) {
    var showDrop by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier.clickable { onClick(subjectWithSeries.subject.id) },

        headlineContent = {
            Text(subjectWithSeries.subject.title)
        },
        overlineContent = {
            Text(subjectWithSeries.series.name)
        },
        trailingContent = {
            Box {
                IconButton(onClick = { showDrop = true }) {
                    Icon(Icons.Default.MoreVert, "more")
                }
                DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Update, "update") },
                        text = { Text("Update") },
                        onClick = {
                            onUpdate(subjectWithSeries.subject.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(subjectWithSeries.subject.id)
                            showDrop = false
                        },
                    )
                }
            }
        },
    )
}
