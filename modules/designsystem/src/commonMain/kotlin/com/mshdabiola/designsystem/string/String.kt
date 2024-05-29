package com.mshdabiola.designsystem.string

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import serieseditor.modules.designsystem.generated.resources.Res
import serieseditor.modules.designsystem.generated.resources.app_name

val appName
    @Composable
    get() = stringResource(Res.string.app_name)
