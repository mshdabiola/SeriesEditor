package com.mshdabiola.ui

import androidx.compose.foundation.text.input.TextFieldState
import com.mshdabiola.seriesmodel.Content
import com.mshdabiola.seriesmodel.Examination
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.seriesmodel.Instruction
import com.mshdabiola.seriesmodel.Option
import com.mshdabiola.seriesmodel.QUESTION_TYPE
import com.mshdabiola.seriesmodel.Question
import com.mshdabiola.seriesmodel.SubjectWithSeries
import com.mshdabiola.seriesmodel.TopicWithCategory
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.SubjectUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList

fun Question.toQuestionUiState(isEdit: Boolean = false) = QuestionUiState(
    id = id,
    number = number,
    examId = examId,
    contents = contents.map {
        it.toItemUi(isEdit)
    }.toImmutableList(),
    options = options?.map {
        it.toOptionUi(isEdit)
    }?.toImmutableList(),
    isTheory = type == QUESTION_TYPE.ESSAY,
    answers = answers.map {
        it.toItemUi(isEdit)
    }?.toImmutableList(),
    instructionUiState = instruction?.toInstructionUiState(),
    topicUiState = topic?.toUi(),
)

fun QuestionUiState.toQuestionWithOptions(examId: Long) = Question(
    id = id,
    number = number,
    examId = examId,
    contents = contents.map { it.toItem() },
    options = options?.map {
        it.toOption(questionId = id, examId)
    },
    type = if (isTheory) QUESTION_TYPE.ESSAY else QUESTION_TYPE.MULTIPLE_CHOICE,
    answers = answers?.map { it.toItem() } ?: emptyList(),
    instruction = instructionUiState?.toInstruction(),
    topic = topicUiState?.toTopic(),
    title = "",
)

fun Option.toOptionUi(isEdit: Boolean = false) =
    OptionUiState(
        id = id,
        nos = number,
        content = contents.map { it.toItemUi(isEdit) }.toImmutableList(),
        isAnswer = isAnswer,
    )

fun OptionUiState.toOption(questionId: Long, examId: Long) =
    Option(
        id = id,
        number = nos,
        questionId = questionId,
        contents = content.map { it.toItem() },
        isAnswer = isAnswer,
        title = "",
    )

fun ItemUiState.toItem() = Content(content = content.text.toString(), type = type)

fun Content.toItemUi(isEdit: Boolean = false) =
    ItemUiState(content = TextFieldState(content), type = type, isEditMode = isEdit)

fun InstructionUiState.toInstruction() = Instruction(
    id = id,
    examId = examId,
    title = title.text.toString(),
    content = content.map { it.toItem() },
)

fun Instruction.toInstructionUiState(isEdit: Boolean = false) =
    InstructionUiState(
        id = id,
        examId = examId,
        title = TextFieldState(title),
        content = content.map { it.toItemUi(isEdit = isEdit) }.toImmutableList(),
    )
fun TopicWithCategory.toUi() = TopicUiState(id = id, topicCategory = topicCategory, name = title)
fun TopicUiState.toTopic() = TopicWithCategory(id = id, topicCategory = topicCategory, title = name)

fun SubjectWithSeries.toUi() = SubjectUiState(subject.id, series.name, subject.title)

fun Examination.toUi() = ExamUiState(
    id = id,
    year,
    duration = duration,
)

fun ExaminationWithSubject.toUi() = ExamUiState(
    id = examination.id,
    year = examination.year,
    duration = examination.duration,
    subject = SubjectUiState(subject.id, series.name, subject.title),
)

fun ExamUiState.toExam() =
    Examination(
        id = id,
        subjectId = subject.id,
        year = year,
        duration = duration,
    )
