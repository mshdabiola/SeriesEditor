/*
 *abiola 2022
 */

package com.mshdabiola.benchmarks.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.mshdabiola.benchmarks.PACKAGE_NAME
import com.mshdabiola.benchmarks.detail.addQuestion
import com.mshdabiola.benchmarks.detail.goBack
import com.mshdabiola.benchmarks.main.addExam
import com.mshdabiola.benchmarks.main.addSubject
import com.mshdabiola.benchmarks.main.goAddQuestionToDScreen
import com.mshdabiola.benchmarks.startActivity
import org.junit.Rule
import org.junit.Test

class GenerateBaselineProfile {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collect(PACKAGE_NAME) {
            startActivity()

            addSubject("Math")
            addSubject("English")

            addExam()
            addExam()

            goAddQuestionToDScreen()
            addQuestion()
            addQuestion()

            goBack()
        }
}
