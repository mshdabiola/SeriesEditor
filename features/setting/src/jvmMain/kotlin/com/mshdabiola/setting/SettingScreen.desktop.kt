package com.mshdabiola.setting

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import org.jetbrains.compose.resources.stringArrayResource
import serieseditor.features.setting.generated.resources.Res
import serieseditor.features.setting.generated.resources.theme

@Preview
@Composable
fun DialogPreview() {
    OptionsDialog(
        modifier = Modifier,
        options = stringArrayResource(Res.array.theme),
        current = 1,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ScreenPreview() {
    SettingScreen(
        modifier = Modifier,
        settingState = SettingState.Success(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.DARK,
        ),
    )
}
