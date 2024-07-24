package com.mshdabiola.testing.util

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class GeneralPathRule(
    val path: String = System.getProperty("user.temp") + "/test",
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        //  generalPath=path
    }
}
