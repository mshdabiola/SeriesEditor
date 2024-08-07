/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.Converter
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.model.ImageUtil.getAppPath
import com.mshdabiola.seriesmodel.QUESTION_TYPE
import com.mshdabiola.seriesmodel.Type
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.toInstructionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class CqViewModel(
    private val examId: Long,
    private val questionId: Long,
    private val questionRepository: IQuestionRepository,
    private val instructionRepository: IInstructionRepository,
    private val examRepository: IExaminationRepository,
    private val settingRepository: ISettingRepository,
    private val topicCategory: ITopicCategory,

) : ViewModel() {

//    private var _question =
//        mutableStateOf(
//            getEmptyQuestion(),
//        )
//    val question: State<QuestionUiState> = _question
//    private val _update = MutableStateFlow(Update.Edit)
//    val update = _update.asStateFlow()

    private val _cqState = MutableStateFlow<CqState>(CqState.Loading())
    val cqState = _cqState.asStateFlow()

    val inputQuestions = TextFieldState("")
    private val convert = Converter()

//    val instructs = instructionRepository
//        .getAllByExamId(examId)
//        .map { instructions ->
//            instructions
//                .map {
//                    it.toInstructionUiState()
//                }
//                .toImmutableList()
//        }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            emptyList<InstructionUiState>().toImmutableList(),
//        )

    var subjectId = -1L

    init {

        viewModelScope.launch {

            val ques = questionRepository
                .getOne(questionId)
                .first()

            _cqState.update {
                CqState.Success(
                    questionUiState = ques?.toQuestionUiState(true) ?: getEmptyQuestion(),
                )
            }

            val examtem = examRepository
                .getOne(examId)
                .first()

            subjectId = examtem?.subject?.id ?: -1L

            val topics = topicCategory
                .getTopicBySubject(subjectId)
            val instruction = instructionRepository
                .getAllByExamId(examId)

            combine(topics, instruction) { t, i -> Pair(t, i) }
                .collectLatest { pair ->
                    _cqState.update {
                        if (it is CqState.Success) {
                            it.copy(
                                topics = pair.first.map { it.toUi() }.toImmutableList(),
                                instructs = pair.second.map { it.toInstructionUiState() }
                                    .toImmutableList(),
                            )
                        } else {
                            it
                        }
                    }
                }
        }
    }

    private fun updateExamType(isObjOnly: Boolean) {
        viewModelScope.launch {
            val exam = examRepository.getOne(examId).firstOrNull() ?: return@launch

//
//            if (exam.isObjectiveOnly != isObjOnly) {
//                examRepository.upsert(exam.copy(isObjectiveOnly = isObjOnly))
//            }
        }
    }

    // question edit logic
    fun onAddOption() {
        var question = (_cqState.value as CqState.Success).questionUiState
        question = question.copy(
            options = question.options
                ?.toMutableList()
                ?.apply {
                    add(
                        OptionUiState(
                            nos = (question.options!!.size + 1).toLong(),
                            content = listOf(
                                ItemUiState(isEditMode = true, focus = true),
                            ).toImmutableList(),
                            isAnswer = false,
                        ),
                    )
                }
                ?.toImmutableList(),

        )

        _cqState.update {
            if (it is CqState.Success) {
                it.copy(questionUiState = question)
            } else {
                it
            }
        }
    }

    fun onAddAnswer(show: Boolean) {
        var question = (_cqState.value as CqState.Success).questionUiState
        question = question.copy(
            answers = if (show) {
                listOf(
                    ItemUiState(isEditMode = true, focus = true),
                ).toImmutableList()
            } else {
                null
            },
        )

        _cqState.update {
            if (it is CqState.Success) {
                it.copy(questionUiState = question)
            } else {
                it
            }
        }
    }

    fun isTheory(isT: Boolean) {
        var question = (_cqState.value as CqState.Success).questionUiState

        question = if (isT) {
            question.copy(
                answers = listOf(
                    ItemUiState(isEditMode = true, focus = false),
                ).toImmutableList(),
                options = emptyList<OptionUiState>().toImmutableList(),
            )
        } else {
            getEmptyQuestion()
        }

        _cqState.update {
            if (it is CqState.Success) {
                it.copy(questionUiState = question.copy(isTheory = isT))
            } else {
                it
            }
        }
    }

    private fun getEmptyQuestion(
        optionNo: Int = 4,
        isTheory: Boolean = false,
    ): QuestionUiState {
        val opNumb = if (isTheory) 0 else optionNo
        return QuestionUiState(
            number = -1,
            examId = examId,
            contents = listOf(
                ItemUiState(isEditMode = true, focus = true),
            ).toImmutableList(),
            options = (1..opNumb)
                .map {
                    OptionUiState(
                        nos = it.toLong(),
                        content = listOf(
                            ItemUiState(isEditMode = true),
                        ).toImmutableList(),
                        isAnswer = false,
                    )
                }.toImmutableList(),
            isTheory = isTheory,
            answers = if (isTheory) {
                listOf(
                    ItemUiState(isEditMode = true),
                ).toImmutableList()
            } else {
                null
            },
        )
    }

    fun addUP(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUiState(isEditMode = true))
            i
        }
    }

    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            it.add(index + 1, ItemUiState(isEditMode = true))
            index + 1
        }
    }

    fun moveUP(questionIndex: Int, index: Int) {
        if (index == 0) {
            return
        }
        editContent(questionIndex) {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

            null
        }
    }

    fun moveDown(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }
            null
        }
    }

    fun delete(questionIndex: Int, index: Int) {
        var removeOption = false
        editContent(questionIndex) {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getAppPath("$examId/${oldItem.content.text}").deleteOnExit()

//                FileManager.delete(
//                    oldItem.content,
//                    subjectId,
//                    examId,
//                    FileManager.ImageType.QUESTION
//                )
            }

            if (questionIndex < 0) {
                if (it.size == 1) {
                    it[index] = ItemUiState(isEditMode = true, focus = true)
                } else {
                    it.removeAt(index)
                }
            } else {
                it.removeAt(index)
                if (it.isEmpty()) {
                    removeOption = true
                }
            }
            null
        }
        if (removeOption) {
            var question = (_cqState.value as CqState.Success).questionUiState
            val options = question.options?.toMutableList()
            val option = options?.removeAt(questionIndex)

            if (option?.id!! > 0) {
                viewModelScope.launch {
                    questionRepository.deleteOption(option.id)
                }
            }

            _cqState.update {
                if (it is CqState.Success) {
                    it.copy(questionUiState = question.copy(options = options.toImmutableList()))
                } else {
                    it
                }
            }
        }
    }

    fun changeType(questionIndex: Int, index: Int, type: Type) {
        editContent(questionIndex) {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getAppPath("$examId/${oldItem.content.text}").deleteOnExit()
            }
            it[index] = ItemUiState(isEditMode = true, type = type)
            index
        }
    }

    private fun editContent(
        questionIndex: Int,
        items: suspend (MutableList<ItemUiState>) -> Int?,
    ) {
        viewModelScope.launch {
            var question = (_cqState.value as CqState.Success).questionUiState

            when (questionIndex) {
                -1 -> {
                    var qItem = question.contents.toMutableList()
                    val i = items(qItem)
                    if (i != null) {
                        qItem = qItem.mapIndexed { index, itemUi ->
                            itemUi.copy(focus = index == i)
                        }.toMutableList()
                    }

                    question = question.copy(contents = qItem.toImmutableList())
                }

                -2 -> {
                    var qItem = question.answers?.toMutableList()
                    if (qItem != null) {
                        val i = items(qItem)
                        if (i != null) {
                            qItem = qItem.mapIndexed { index, itemUi ->
                                itemUi.copy(focus = index == i)
                            }.toMutableList()
                        }

                        question = question.copy(answers = qItem?.toImmutableList())
                    }
                }

                else -> {
                    val options = question.options!!.toMutableList()
                    var option = options[questionIndex]
                    var qItem = option.content.toMutableList()
                    val i = items(qItem)
                    if (i != null) {
                        qItem = qItem.mapIndexed { index, itemUi ->
                            itemUi.copy(focus = index == i)
                        }.toMutableList()
                    }

                    option = option.copy(content = qItem.toImmutableList())
                    options[questionIndex] = option

                    question = question.copy(options = options.toImmutableList())
                }
            }
            _cqState.update {
                if (it is CqState.Success) {
                    it.copy(questionUiState = question)
                } else {
                    it
                }
            }
        }
    }

    private fun log(msg: String) {
//        co.touchlab.kermit.Logger.e(msg)
    }

    fun onAddQuestion() {
        viewModelScope.launch {
            var question2 = (_cqState.value as CqState.Success).questionUiState

            _cqState.update {
                CqState.Loading()
            }

            val questions = questionRepository
                .getByExamId(examId)
                .first()

            val number =
                if (question2.number == -1L) questions.filter { (it.type == QUESTION_TYPE.ESSAY) == question2.isTheory }.size.toLong() + 1 else question2.number
            question2 = question2.copy(number = number)

            val allIsObj =
                questions.all { (it.type == QUESTION_TYPE.ESSAY).not() } && question2.isTheory.not()
            questionRepository.upsert(question2.toQuestionWithOptions(examId = examId))
            updateExamType(isObjOnly = allIsObj)
//
//                _cqState.update {
//                    if (it is CqState.Success){
//                        it.copy(questionUiState = question)
//                    }
//                    else it
//                }
//
//                _question.value = getEmptyQuestion(question2.options!!.size, question2.isTheory)
//
// // remove from save
//                val inst = settingRepository.questions
//                    .first()
//                    .toMutableMap()
//                inst.remove(examId)
//                settingRepository.setCurrentQuestion(inst)

            _cqState.update {
                CqState.Loading(true)
            }
        }
    }

    fun changeView(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)

            if (it[index].isEditMode) index else null
        }
    }

    fun onTopicChange(index: Int) {
        var question = (_cqState.value as CqState.Success).questionUiState
        val topics = (_cqState.value as CqState.Success).topics

        question = question.copy(topicUiState = topics.getOrNull(index))

        _cqState.update {
            if (it is CqState.Success) {
                it.copy(questionUiState = question)
            } else {
                it
            }
        }
    }

    fun onInstructionChange(index: Int) {
        var question = (_cqState.value as CqState.Success).questionUiState
        val instructions = (_cqState.value as CqState.Success).instructs

        question =
            question.copy(instructionUiState = instructions.getOrNull(index))

        _cqState.update {
            if (it is CqState.Success) {
                it.copy(questionUiState = question)
            } else {
                it
            }
        }
    }

    fun onAddQuestionsFromInput() {
        viewModelScope.launch {
            _cqState.update {
                CqState.Loading()
            }

            val saveQuestions = questionRepository.getByExamId(examId)
                .first()
            val objNumber = saveQuestions
                .filter { (it.type == QUESTION_TYPE.MULTIPLE_CHOICE) }
                .maxOfOrNull { it.number } ?: 0
            val theoryNumber = saveQuestions
                .filter { (it.type == QUESTION_TYPE.ESSAY) }
                .maxOfOrNull { it.number } ?: 0

            val questions = convert.textToQuestion(
                inputQuestions.text.toString(),
                examId,
                objNumber + 1,
                theoryNumber + 1,
            )

            questions.forEach {
                questionRepository.upsert(it)
            }

            _cqState.update {
                CqState.Loading(true)
            }
        }
    }
}
