/*
 *abiola 2024
 */

package com.mshdabiola.benchmarks.main

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import com.mshdabiola.benchmarks.flingElementDownUp

fun MacrobenchmarkScope.goAddQuestionToDScreen() {
    val selector = By.res("main:list")
    device.wait(Until.hasObject(selector), 5000)

    val examList = device.findObject(selector)


    examList.children[0].click()
    // Wait until saved title are shown on screen
}


fun MacrobenchmarkScope.addSubject(subject: String) {

    val buttonSelector = By.res("add")

    device.wait(Until.hasObject(buttonSelector), 5000)

    device.findObject(buttonSelector)
        .click()
    device.waitForIdle()


    device.findObject(By.res("main:subject"))
        .text = subject

    device.findObject(By.res("main:add_subject"))

    device.findObject(By.res("handle"))
        .fling(Direction.DOWN)
}

fun MacrobenchmarkScope.addExam() {

    val buttonSelector = By.res("add")

    device.wait(Until.hasObject(buttonSelector), 5000)

    device.findObject(buttonSelector)
        .click()

    device.findObject(By.res("main:duration"))
        .text = "24"
    device.findObject(By.res("main:year"))
        .text = "2014"

    device.findObject(By.res("main:add_exam"))
        .click()

    device.findObject(By.res("handle"))
        .fling(Direction.DOWN)
}

fun MacrobenchmarkScope.mainWaitForContent() {
    // Wait until content is loaded by checking if topics are loaded
    //  device.wait(Until.gone(By.res("loadingWheel")), 5_000)
    // Sometimes, the loading wheel is gone, but the content is not loaded yet
    // So we'll wait here for topics to be sure
    //   val obj = device.waitAndFindObject(By.res("forYou:topicSelection"), 10_000)
    // Timeout here is quite big, because sometimes data loading takes a long time!
    //   obj.wait(untilHasChildren(), 60_000)
}
