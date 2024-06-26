package com.mshdabiola.data.model

import com.mshdabiola.datastore.model.ContentSer
import com.mshdabiola.datastore.model.CurrentExamSer
import com.mshdabiola.datastore.model.InstructionSer
import com.mshdabiola.datastore.model.OptionSer
import com.mshdabiola.datastore.model.QuestionSer
import com.mshdabiola.datastore.model.TopicSer
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.generalmodel.Content
import com.mshdabiola.generalmodel.CurrentExam
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Option
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.model.UserData

fun UserData.toSer() =
    UserDataSer(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

fun UserDataSer.toData() =
    UserData(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

fun Instruction.toSer() = InstructionSer(id, examId, title, content.map { it.toSer() })

fun InstructionSer.asModel() = Instruction(id, examId, title, contentSer.map { it.asModel() })

fun Topic.toSer() = TopicSer(id, subjectId, title)
fun TopicSer.asModel() = Topic(id, subjectId, title)

fun Option.toSer() = OptionSer(
    id = id,
    number = number,
    questionId = questionId,
    examId = examId,
    title = title,
    contents = contents.map { it.toSer() },
    isAnswer = isAnswer,
)

fun OptionSer.asModel() = Option(
    id = id,
    number = number,
    questionId = questionId,
    examId = examId,
    title = title,
    contents = contents.map { it.asModel() },
    isAnswer = isAnswer,
)

fun Content.toSer() = ContentSer(content, type)
fun ContentSer.asModel() = Content(content, type)

fun Question.asSer() = QuestionSer(
    id,
    number,
    examId,
    title,
    contents.map { it.toSer() },
    answers.map { it.toSer() },
    options?.map { it.toSer() },
    isTheory,
    instruction?.toSer(),
    topic?.toSer(),
)

fun QuestionSer.asModel() = Question(
    id,
    number,
    examId,
    title,
    contentSers.map { it.asModel() },
    answers.map { it.asModel() },
    optionSers?.map { it.asModel() },
    isTheory,
    instructionSer?.asModel(),
    topicSer?.asModel(),
)

fun CurrentExam.toSer() =
    CurrentExamSer(id, currentTime, totalTime, isSubmit, paperIndex, examPart, choose)

fun CurrentExamSer.asModel() =
    CurrentExam(id, currentTime, totalTime, isSubmit, paperIndex, examPart, choose)
