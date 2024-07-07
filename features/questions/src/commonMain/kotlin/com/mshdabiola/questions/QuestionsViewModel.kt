/*
 *abiola 2022
 */

package com.mshdabiola.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val examId: Long,
    private val questionRepository: IQuestionRepository,
) : ViewModel() {

    val questions = MutableStateFlow<Result<List<QuestionUiState>>>(Result.Loading)

//    val questions = mutableStateOf(emptyList<QuestionUiState>().toImmutableList())

    init {
        viewModelScope.launch {
            questionRepository
                .getByExamId(examId)
                .map { notes ->
                    notes
                        .map { it.toQuestionUiState() }
                        .sortedBy { it.number }
                        .sortedBy { it.isTheory }
                }
                .asResult()
                .collectLatest { result ->
                    //
                    questions.update { result }
                }
        }
    }

    // question logic
    fun onDeleteQuestion(id: Long) {
        rearrangeAndSave { questionUiStates ->
            val index = questionUiStates.indexOfFirst { it.id == id }
            questionUiStates.removeAt(index)
            questionRepository.delete(id)
        }
    }

    fun onMoveUpQuestion(id: Long) {
        val index = (questions.value as Result.Success)
            .data.indexOfFirst { it.id == id }
        if (index == 0) {
            return
        }

        rearrangeAndSave {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up
        }
    }

    fun onMoveDownQuestion(id: Long) {
        val q = (questions.value as Result.Success).data
        val index = q.indexOfFirst { it.id == id }
        if (index == q.lastIndex) {
            return
        }

        rearrangeAndSave {
            val downIndex = index + 1
            val down = it[downIndex]
            it[downIndex] = it[index]
            it[index] = down
        }
    }

    var answerJob: Job? = null
    fun onAnswerClick(questionId: Long, optionId: Long) {
        if (answerJob != null) {
            return
        }
        answerJob = viewModelScope.launch {
            val questionUiStates = (questions.value as Result.Success).data
            val questionIndex = questionUiStates.indexOfFirst { it.id == questionId }
            var question = questionUiStates[questionIndex]
            var options = question
                .options
                ?.map {
                    it.copy(isAnswer = it.id == optionId)
                }

            question = question.copy(
                options = options?.toImmutableList(),
            )
            questionRepository.upsert(question.toQuestionWithOptions(examId))
            answerJob = null
        }
    }

    private var job: Job? = null
    private fun rearrangeAndSave(onEdit: suspend (MutableList<QuestionUiState>) -> Unit) {
        if (job != null) {
            return
        }

        job = viewModelScope.launch {
            var list = (questions.value as Result.Success).data.toMutableList()
            onEdit(list)
            var theory = 0L
            var obj = 0L
            list = list.map { questionUiState ->
                if (questionUiState.isTheory) {
                    theory += 1
                    questionUiState.copy(number = theory)
                } else {
                    obj += 1
                    questionUiState.copy(number = obj)
                }
            }.toMutableList()

            // save
            questionRepository.upsertMany(list.map { it.toQuestionWithOptions(examId) })
            job = null
        }
    }
}
