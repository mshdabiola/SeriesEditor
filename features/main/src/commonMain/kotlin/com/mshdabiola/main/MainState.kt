package com.mshdabiola.main

import com.mshdabiola.seriesmodel.Series

data class MainState(
    val series: List<Series> = emptyList(),
    val subjectNumber: Int = 0,
    val examNumber: Int = 0,
    val questionNumber: Int = 0,
    val studentNumber: Int = 0,
)
