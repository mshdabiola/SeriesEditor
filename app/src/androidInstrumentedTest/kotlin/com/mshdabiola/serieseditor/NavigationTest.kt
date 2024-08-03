package com.mshdabiola.serieseditor

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.testing.dataTestModule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.properties.ReadOnlyProperty

/**
 * Tests all the navigation flows that are handled by the navigation library.
 */
class NavigationTest {
    private val logger2 = Logger(
        loggerConfigInit(platformLogWriter()),
        "AndroidLogger",
    )
    private val logModule = module {
        single {
            logger2
        }
    }
    private val instrumentedTestModule = module {
        logModule
    }

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(instrumentedTestModule),
    )

    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 3)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(resId) }

    // The strings used for matching in these tests
    // private val navigateUp by composeTestRule.stringResource(FeatureForyouR.string.feature_foryou_navigate_up)

    @Test
    fun firstScreen_isForYou() {
        composeTestRule.apply {
            // VERIFY for you is selected
            //  this.waitUntil()
            onNodeWithText("Add", useUnmergedTree = true).assertExists()
        }
    }
}

class KoinTestRule(
    private val modules: List<Module>,
) : TestWatcher() {
    override fun starting(description: Description) {
        loadKoinModules(dataTestModule)
//        startKoin {
//            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
//            modules(modules)
//        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}
