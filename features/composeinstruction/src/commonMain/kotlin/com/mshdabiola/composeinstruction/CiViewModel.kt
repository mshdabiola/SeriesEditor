/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.Converter
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.seriesmodel.Instruction
import com.mshdabiola.seriesmodel.Type
import com.mshdabiola.model.ImageUtil.getAppPath
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.toItem
import com.mshdabiola.ui.toItemUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CiViewModel(
    private val examId: Long,
    private val introductionId: Long,
    private val instructionRepository: IInstructionRepository,
    private val settingRepository: ISettingRepository,

) : ViewModel() {

    val instructionInput = TextFieldState()

    private val _ciState = MutableStateFlow<CiState>(CiState.Loading())
    val ciState = _ciState.asStateFlow()
    val title = TextFieldState()

    init {

        viewModelScope.launch {
            val instruct = instructionRepository
                .getOne(introductionId)
                .first()

            if (instruct != null) {

                title.edit {
                    append(instruct.title)
                }
                _ciState.update {
                    CiState.Success(
                        id = introductionId,
                        examId = examId,
                        content = instruct.content.map { it.toItemUi() }.toImmutableList(),
                    )
                }
            } else {
                _ciState.update {
                    CiState.Success(
                        id = introductionId,
                        examId = examId,
                        content = listOf(ItemUiState(isEditMode = true)).toImmutableList(),
                    )
                }
            }
        }
    }

    fun onAdd() {
        viewModelScope.launch {
            val success = _ciState.value as CiState.Success
            _ciState.update {
                CiState.Loading(false)
            }

            instructionRepository.upsert(
                Instruction(
                    success.id,
                    examId,
                    title.text.toString(),
                    success.content.map { it.toItem() },
                ),
            )

            _ciState.update {
                CiState.Loading(true)
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
        val contents = (ciState.value as CiState.Success).content
        if (index == contents.lastIndex) {
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
        val contents = (ciState.value as CiState.Success).content

        if (contents.size == 1) {
            return
        }
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getAppPath("$examId/${oldItem.content.text}").deleteOnExit()
            }
            it.removeAt(index)
            null
        }
    }

    fun changeType(index: Int, type: Type) {
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getAppPath("$examId/${oldItem.content.text}").deleteOnExit()
            }
            it[index] = ItemUiState(isEditMode = true, type = type)
            index
        }
    }

    private fun editContentInstruction(
        onItems: suspend (MutableList<ItemUiState>) -> Int?,
    ) {
        val contents = (ciState.value as CiState.Success).content

        viewModelScope.launch {
            var items = contents.toMutableList()
            val i = onItems(items)
            if (i != null) {
                items = items.mapIndexed { index, itemUi ->
                    itemUi.copy(focus = index == i)
                }.toMutableList()
            }

            _ciState.update {
                CiState.Success(
                    id = introductionId,
                    examId = examId,
                    content = items.toImmutableList(),
                )
            }
        }
    }

    fun onAddInstructionInput() {
        viewModelScope.launch {
            _ciState.update {
                CiState.Loading(false)
            }

            val instructions = Converter().textToInstruction(instructionInput.text.toString(), examId)

            instructions.forEach {
                instructionRepository.upsert(
                    it,
                )
            }

            _ciState.update {
                CiState.Loading(true)
            }
        }
    }
}
