/*
 *abiola 2022
 */

package com.mshdabiola.instructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.toInstructionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstructionsViewModel(
    private val examId: Long,
    private val instructionRepository: IInstructionRepository,
) : ViewModel() {

    val instructions = MutableStateFlow<Result<List<InstructionUiState>>>(Result.Loading)
//    val questions = mutableStateOf(emptyList<QuestionUiState>().toImmutableList())

    init {
        viewModelScope.launch {
            instructionRepository
                .getAllByExamId(examId)
                .map { notes ->
                    notes
                        .map { it.toInstructionUiState() }
                }
                .asResult()
                .collectLatest { result ->
                    instructions.update { result }
                }
        }
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            instructionRepository.delete(id)
        }
    }
}
