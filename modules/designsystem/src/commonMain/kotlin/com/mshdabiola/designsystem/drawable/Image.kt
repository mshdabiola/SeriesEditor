package com.mshdabiola.designsystem.drawable

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import serieseditor.modules.designsystem.generated.resources.Res
import serieseditor.modules.designsystem.generated.resources.icon

val defaultAppIcon
    @Composable
    get() = painterResource(Res.drawable.icon)

// imageResource(Res.drawable.icon)
