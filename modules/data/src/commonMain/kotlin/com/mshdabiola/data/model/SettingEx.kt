package com.mshdabiola.data.model

import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.model.UserData

fun UserData.toSer() =
    UserDataSer(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

fun UserDataSer.toData() =
    UserData(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)
