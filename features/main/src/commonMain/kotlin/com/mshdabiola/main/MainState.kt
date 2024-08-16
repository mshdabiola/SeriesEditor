package com.mshdabiola.main

import com.mshdabiola.seriesmodel.Series

data class MainState(
    val series: List<Series> = emptyList(),

    )