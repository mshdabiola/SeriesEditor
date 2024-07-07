/*
 *abiola 2024
 */

package com.mshdabiola.benchmarks.detail

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.goBack() {
    val selector = By.res("back")

    device.wait(Until.hasObject(selector), 5000)

    val backButton = device.findObject(selector)
    backButton.click()
    device.waitForIdle()
    // Wait until saved title are shown on screen
}

fun MacrobenchmarkScope.addQuestion() {
    val buttonSelector = By.res("add")

    device.wait(Until.hasObject(buttonSelector), 5000)

    device.findObject(buttonSelector)
        .click()
    val question = listOf("what is your name", "abiola", "moshood", "ade", "hammed", "jerry")

    val contents = device.findObjects(By.res("main:duration"))
    contents.forEachIndexed { index, uiObject2 ->

        uiObject2.text = question[index]
        device.waitForIdle()

    }

    device.findObject(By.res("main:add_question"))
        .click()

    device.findObject(By.res("handle"))
        .fling(Direction.DOWN)

    // Wait until saved title are shown on screen
}
