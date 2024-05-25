package com.mshdabiola.designsystem.drawable

import androidx.compose.runtime.Composable
import serieseditor.modules.designsystem.generated.resources.Res
import serieseditor.modules.designsystem.generated.resources.icon
import org.jetbrains.compose.resources.painterResource

val defaultAppIcon
    @Composable
    get() = painterResource(Res.drawable.icon)

// imageResource(Res.drawable.icon)
