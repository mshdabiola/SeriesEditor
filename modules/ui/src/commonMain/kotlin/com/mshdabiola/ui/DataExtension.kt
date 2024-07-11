package com.mshdabiola.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.mshdabiola.generalmodel.Content
import com.mshdabiola.generalmodel.Examination
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Option
import com.mshdabiola.generalmodel.QUESTION_TYPE
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.generalmodel.Subject
import com.mshdabiola.generalmodel.Topic
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
    type = if (isTheory)QUESTION_TYPE.ESSAY else QUESTION_TYPE.MULTIPLE_CHOICE,
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

@OptIn(ExperimentalFoundationApi::class)
fun ItemUiState.toItem() = Content(content = content.text.toString(), type = type)

@OptIn(ExperimentalFoundationApi::class)
fun Content.toItemUi(isEdit: Boolean = false) =
    ItemUiState(content = TextFieldState(content), type = type, isEditMode = isEdit)

@OptIn(ExperimentalFoundationApi::class)
fun InstructionUiState.toInstruction() = Instruction(
    id = id,
    examId = examId,
    title = title.text.toString(),
    content = content.map { it.toItem() },
)

@OptIn(ExperimentalFoundationApi::class)
fun Instruction.toInstructionUiState(isEdit: Boolean = false) =
    InstructionUiState(
        id = id,
        examId = examId,
        title = TextFieldState(title),
        content = content.map { it.toItemUi(isEdit = isEdit) }.toImmutableList(),
    )

fun Topic.toUi() = TopicUiState(id = id, subjectId = subjectId, name = title)
fun TopicUiState.toTopic() = Topic(id = id, subjectId = subjectId, title = name)

fun Subject.toUi() = SubjectUiState(id, seriesId, title)
fun SubjectUiState.toSubject() = Subject(id, seriesId, name)

fun Examination.toUi() = ExamUiState(
    id = id,
    year,
    isObjectiveOnly = true,
    duration = duration,
)

fun ExamUiState.toExam() =
    Examination(
        id = id,
        subjectId = subject.id,
        year = year,
        duration = duration,
    )
