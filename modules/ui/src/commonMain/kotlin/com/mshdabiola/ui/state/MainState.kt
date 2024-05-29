package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainState(
    val title: String = "Title",
    val currentExam: ExamUiState? = null,
    val listOfAllExams: ImmutableList<ExamUiState>,
    val currentSectionIndex: Int = 0,
    val questions: ImmutableList<ImmutableList<QuestionUiState>> =
        listOf(emptyList<QuestionUiState>().toImmutableList()).toImmutableList(),
    val choose: ImmutableList<ImmutableList<Int>> =
        listOf(emptyList<Int>().toImmutableList()).toImmutableList(),
    val sections: ImmutableList<Section> = emptyList<Section>().toImmutableList(),
    val score: ScoreUiState? = null,
    val examPart: Int = 0,
    val currentTime: Long = 0,
    val totalTime: Long = 4,
    val isSubmit: Boolean = false,
)
