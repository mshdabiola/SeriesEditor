package com.mshdabiola.data

import com.mshdabiola.generalmodel.Content
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Option
import com.mshdabiola.generalmodel.QUESTION_TYPE
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.generalmodel.Topic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Converter {

    fun textToTopic(
        path: String,
        categoryId: Long,
    ): List<Topic> {
        return path
            .split(Regex("\\s*\\*\\s*"))
            .filter { it.isNotBlank() }
            .map {
                Topic(
                    categoryId = categoryId, // Todo: get category id
                    title = it,
                )
            }
    }

    suspend fun textToInstruction(
        path: String,
        examId: Long,
    ): List<Instruction> {
        return withContext(Dispatchers.IO) {
            path
                .split(Regex("\\s*\\*\\s*"))
                .filter { it.isNotBlank() }
                .map {
                    Instruction(
                        examId = examId,
                        title = "",
                        content = listOf(itemise(it)),
                    )
                }
        }
    }

    fun textToQuestion(
        path: String,
        examId: Long,
        nextObjNumber: Long,
        nextTheoryNumber: Long,
    ): List<Question> {
        val list = mutableListOf<Question>()
        var options = mutableListOf<String>()
        var answer: String? = null
        var isTheory = false
        var objNo = nextObjNumber
        var thrNo = nextTheoryNumber
        var question: String? = null
        val s = path
            .split(Regex("\\s*\\*\\s*"))
            .toMutableList()
//            println(s.joinToString())
        s.removeAt(0)

        s.chunked(2) {
            Pair(it[0].trim(), it[1])
        }
            .forEachIndexed { _, pair ->
                when (pair.first) {
                    "q" -> {
                        if (question != null) {
                            if (isTheory) {
                                list.add(
                                    convertThe(
                                        theoryNo = thrNo,
                                        content = question!!,
                                        examId = examId,
                                        answer = answer ?: "",
                                    ),
                                )

                                thrNo += 1
                            } else {
                                list.add(
                                    convertObj(
                                        questionNos = objNo,
                                        content = question!!,
                                        examId = examId,
                                        options = options,
                                    ),
                                )
                                objNo += 1
                            }
                            isTheory = false
                            answer = null
                            question = null
                            options = mutableListOf()
                        }
                        question = pair.second
                    }

                    "o" -> {
                        options.add(pair.second)
                    }

                    "t" -> {
                        isTheory = (pair.second.toIntOrNull() ?: 0) == 1
                    }

                    "a" -> {
                        answer = pair.second
                    }
                }
            }
        if (question != null) {
            if (isTheory) {
                list.add(
                    convertThe(
                        theoryNo = thrNo,
                        content = question!!,
                        examId = examId,
                        answer = answer ?: "",
                    ),
                )
            } else {
                list.add(
                    convertObj(
                        questionNos = objNo,
                        content = question!!,
                        examId = examId,
                        options = options,
                    ),
                )
            }
        }

        return list
    }

    private fun convertObj(
        questionNos: Long,
        content: String,
        examId: Long,
        options: List<String>,
    ): Question {
        val opti = options
            .mapIndexed { index, s ->
                Option(
                    number = index + 1L,
                    questionId = questionNos,
                    contents = listOf(itemise(s)),
                    isAnswer = false,
                    title = "",
                    id = -1,
                )
            }

        return Question(
            number = questionNos,
            examId = examId,
            contents = listOf(itemise(content)),
            options = opti,
            type = if (opti.isEmpty()) QUESTION_TYPE.ESSAY else QUESTION_TYPE.MULTIPLE_CHOICE,
            answers = emptyList(),
            instruction = null,
            topic = null,
        )
    }

    private fun convertThe(
        theoryNo: Long,
        content: String,
        examId: Long,
        answer: String,
    ): Question {
        return Question(
            number = theoryNo,
            examId = examId,
            contents = listOf(itemise(content)),
            options = emptyList(),
            type = QUESTION_TYPE.ESSAY,
            answers = listOf(itemise(answer)),
            instruction = null,
            topic = null,
        )
    }

    private fun itemise(string: String): Content {
        return Content(content = string)
    }
}
