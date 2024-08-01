package com.mshdabiola.data

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConverterTest {

    @Test
    fun toQuestionTest() = runTest {
        val converter = Converter()

        val questions = converter.textToQuestion(
            """
             *q* What is your name?
               *o* John
               *o* Jane
               *o* Joe
               *o* Jack
               *a* John
              *q* What is your age?
               *o* 10
               *o* 20
               *o* 30
               *o* 40
               *a* 20
            """.trimIndent(),
            4,
            4,
            4,
        )

        assertEquals(9, questions.size)
    }
}
