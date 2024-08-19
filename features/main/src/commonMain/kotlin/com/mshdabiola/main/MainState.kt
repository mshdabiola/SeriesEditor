package com.mshdabiola.main

import com.mshdabiola.seriesmodel.Series
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.seriesmodel.UserType

data class MainState(
    val series: List<Series> = emptyList(),
    val user: User = User(
        id = 1,
        name = "Abiola",
        type = UserType.TEACHER,
        password = "123456",
        imagePath = "",
        points = 1,
    ),
    val subjectNumber: Int = 0,
    val examNumber: Int = 0,
    val questionNumber: Int = 0,
    val studentNumber: Int = 0,
)
