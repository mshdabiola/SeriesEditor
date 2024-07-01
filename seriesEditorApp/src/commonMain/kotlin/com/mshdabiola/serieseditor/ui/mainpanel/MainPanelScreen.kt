package com.mshdabiola.serieseditor.ui.mainpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.composesubject.SubjectRoute
import com.mshdabiola.main.MainRoute
import com.mshdabiola.template.ComposeExaminationRoute


@Composable
fun MainPaneScreen(
    modifier: Modifier = Modifier,
    navigateToQuestion: (Long) -> Unit,
    subjectId: Long
) {
    Row(modifier){
        MainRoute(
            modifier = modifier.weight(0.6f),
            navigateToQuestion = navigateToQuestion,
            subjectId = subjectId
        )
        Column(Modifier.weight(0.4f)){
            SubjectRoute(
                modifier = modifier,
                subjectId = -1
            )

            ComposeExaminationRoute(
                modifier = modifier,
                examId = -1
            )


        }

    }

}