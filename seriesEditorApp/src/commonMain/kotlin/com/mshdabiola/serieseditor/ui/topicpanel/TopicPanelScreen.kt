package com.mshdabiola.serieseditor.ui.topicpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composetopic.navigation.COMPOSE_TOPIC_ROUTE
import com.mshdabiola.composetopic.navigation.composeTopicScreen
import com.mshdabiola.composetopic.navigation.navigateToComposeTopic
import com.mshdabiola.topics.navigation.TOPIC_ROUTE
import com.mshdabiola.topics.navigation.topicScreen


@Composable
fun TopicPaneScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    subjectId: Long,
) {


    val topicNav = rememberNavController()
    val ctNav = rememberNavController()

    Row(Modifier.fillMaxSize()) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = TOPIC_ROUTE,
            navController = topicNav,
        ) {
            topicScreen(
                modifier = Modifier.fillMaxSize(),
                onShowSnack = onShowSnackbar,
                subjectId = subjectId,
                navigateToComposeTopic = ctNav::navigateToComposeTopic,
            )
        }
        Column(Modifier.weight(0.4f)) {
            NavHost(
                navController = ctNav,
                startDestination = COMPOSE_TOPIC_ROUTE,
                modifier = Modifier,

                ) {
                composeTopicScreen(
                    modifier = Modifier,
                    onShowSnack = onShowSnackbar,
                    onFinish = {
                        ctNav.popBackStack()
                        if (ctNav.currentDestination == null) {
                            ctNav.navigateToComposeTopic(
                                subjectId,
                                -1,
                            )
                        }
                    },
                    subjectId = subjectId,

                    )
            }
        }

    }


}