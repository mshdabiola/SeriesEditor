/*
 *abiola 2024
 */

package com.mshdabiola.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
    val userId: Long,
)
