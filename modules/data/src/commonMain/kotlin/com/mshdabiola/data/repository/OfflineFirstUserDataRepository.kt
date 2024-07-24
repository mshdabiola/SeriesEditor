/*
 *abiola 2024
 */

package com.mshdabiola.data.repository

import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.datastore.Store
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OfflineFirstUserDataRepository(
    private val settings: Store,
    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        settings
            .userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        settings.updateUserData {
            it.copy(themeBrand)
        }
        analyticsHelper.logThemeChanged(themeBrand.name)
    }



    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        settings.updateUserData {
            it.copy(darkThemeConfig = darkThemeConfig)
        }
        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        settings.updateUserData {
            it.copy(useDynamicColor = useDynamicColor)
        }
        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        settings.updateUserData {
            it.copy(shouldHideOnboarding = shouldHideOnboarding)
        }
        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }

    override suspend fun setUserId(id: Long) {
        settings.updateUserData {
            it.copy(userId = id)
        }
    }
}
