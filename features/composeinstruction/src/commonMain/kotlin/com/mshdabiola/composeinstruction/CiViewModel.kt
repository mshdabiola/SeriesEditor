/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Update
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.model.ImageUtil.getGeneralDir
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.toInstruction
import com.mshdabiola.ui.toInstructionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class CiViewModel(
    private val examId: Long,
    private val introductionId:Long,
    private val instructionRepository: IInstructionRepository,
    private val settingRepository: ISettingRepository,

    ) : ViewModel() {

    val instructionInput= TextFieldState()

    private val defaultInstruction = InstructionUiState(
        examId = examId,
        title = TextFieldState(),
        content = listOf(ItemUiState(isEditMode = true)).toImmutableList(),
    )
    private val _instructionUiState = mutableStateOf(
        defaultInstruction,
    )
    val instructionUiState: State<InstructionUiState> = _instructionUiState

    // instruction logic

    private val _update = MutableStateFlow(Update.Edit)
    val update=_update.asStateFlow()

    fun onAdd() {
        viewModelScope.launch {
            _update.update {
                Update.Saving
            }
            delay(5000)
            instructionRepository.upsert(
                instructionUiState.value.toInstruction(),
            )

            _instructionUiState.value = defaultInstruction
            _update.update {
                Update.Success
            }
        }
    }

    fun addUp(index: Int) {
        editContentInstruction() {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUiState(isEditMode = true))
            i
        }
    }

    fun addDown(index: Int) {
        editContentInstruction() {
            it.add(index + 1, ItemUiState(isEditMode = true))

            index + 1
        }
    }

    fun moveUp(index: Int) {
        if (index == 0) {
            return
        }
        editContentInstruction() {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

            null
        }
    }

    fun moveDown(index: Int) {
        if (index == instructionUiState.value.content.lastIndex) {
            return
        }
        editContentInstruction() {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }

            null
        }
    }

    fun changeView(index: Int) {
        editContentInstruction() {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)
            if (!item.isEditMode) index else null
        }
    }

    fun deleteInstruction(index: Int) {
        if (instructionUiState.value.content.size == 1) {
            return
        }
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content.text.toString(), examId).deleteOnExit()
            }
            it.removeAt(index)
            null
        }
    }

    fun changeType(index: Int, type: Type) {
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content.text.toString(), examId).deleteOnExit()
            }
            it[index] = ItemUiState(isEditMode = true, type = type)
            index
        }
    }



    private fun editContentInstruction(
        onItems: suspend (MutableList<ItemUiState>) -> Int?,
    ) {
        viewModelScope.launch {
            var items = instructionUiState.value.content.toMutableList()
            val i = onItems(items)
            if (i != null) {
                items = items.mapIndexed { index, itemUi ->
                    itemUi.copy(focus = index == i)
                }.toMutableList()
            }
            _instructionUiState.value = instructionUiState
                .value
                .copy(
                    content = items.toImmutableList(),
                )
        }
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            instructionRepository.delete(id)
        }
    }

    private fun log(msg: String) {
//        co.touchlab.kermit.Logger.e(msg)
    }

    fun onAddInstructionInput() {
        TODO("Not yet implemented")
    }
}
