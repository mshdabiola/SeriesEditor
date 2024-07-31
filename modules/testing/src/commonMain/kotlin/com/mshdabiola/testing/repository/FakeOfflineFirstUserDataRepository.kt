/*
 *abiola 2024
 */

package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FakeOfflineFirstUserDataRepository : UserDataRepository {
    private val _userData = MutableStateFlow(
        UserData(
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            userId = 1,
            shouldHideOnboarding = true,
            themeBrand = ThemeBrand.DEFAULT,
            useDynamicColor = false,
        ),
    )
    override val userData: Flow<UserData>
        get() = _userData.asStateFlow()

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        _userData.value = _userData.value.copy(themeBrand = themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        _userData.value = _userData.value.copy(darkThemeConfig = darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        _userData.value = _userData.value.copy(useDynamicColor = useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        _userData.value = _userData.value.copy(shouldHideOnboarding = shouldHideOnboarding)
    }

    override suspend fun setUserId(id: Long) {
        _userData.value = _userData.value.copy(userId = id)
    }
}
