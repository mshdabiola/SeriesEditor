package com.mshdabiola.serieseditor

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.mshdabiola.serieseditor.ui.SeriesEditorAppState
import com.mshdabiola.serieseditor.ui.rememberAppState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SkAppState {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: SeriesEditorAppState

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun currentDestination() = runTest {
        var currentDestination: String? = null

        composeTestRule.setContent {
            val density = LocalDensity.current
            val navController = rememberTestNavController()

            state = rememberAppState(
                navController = navController,
                windowSizeClass = WindowSizeClass.calculateFromSize(
                    size = DpSize(456.dp, 456.dp),
                ),
            )

            // Update currentDestination whenever it changes
            currentDestination = state.currentDestination?.route

            // Navigate to destination b once
            LaunchedEffect(Unit) {
                navController.navigate("b")
            }
        }

        assertEquals("b", currentDestination)
    }
}

@Composable
private fun rememberTestNavController(): NavHostController {
    return remember {
        NavHostController().apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = "a") {
                composable("a") { }
                composable("b") { }
                composable("c") { }
            }
        }
    }
}
