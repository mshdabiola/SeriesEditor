package com.mshdabiola.serieseditor.ui

sealed class Screen {
    data class Main(val subjectId:Long):Screen()
    data object Other :Screen()
}