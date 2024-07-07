/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Now in Android theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */

lateinit var extendedColorScheme :ExtendedColorScheme

@Composable
fun SeriesEditorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    extendedColorScheme=if (darkTheme) extendedDark else extendedLight
    val colorScheme = when {
        androidTheme -> if (darkTheme) highContrastDarkColorScheme else highContrastLightColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            getDynamicColor(darkTheme)
        }

        else -> if (darkTheme) darkScheme else lightScheme
    }
//

    // Gradient colors
    val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )
    val gradientColors = when {
        androidTheme -> if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
        !disableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
        else -> defaultGradientColors
    }
    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = when {
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }
    val tintTheme = when {
        androidTheme -> TintTheme()
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SkTypography,
            content = content,
        )
    }
}

expect fun supportsDynamicTheming(): Boolean

@Composable
expect fun getDynamicColor(darkTheme: Boolean): ColorScheme


val primaryLight = Color(0xFF3D5F90)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFD5E3FF)
val onPrimaryContainerLight = Color(0xFF001C3B)
val secondaryLight = Color(0xFF555F71)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD9E3F8)
val onSecondaryContainerLight = Color(0xFF121C2B)
val tertiaryLight = Color(0xFF6E5676)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFF8D8FF)
val onTertiaryContainerLight = Color(0xFF27132F)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFF9F9FF)
val onBackgroundLight = Color(0xFF191C20)
val surfaceLight = Color(0xFFF9F9FF)
val onSurfaceLight = Color(0xFF191C20)
val surfaceVariantLight = Color(0xFFE0E2EC)
val onSurfaceVariantLight = Color(0xFF43474E)
val outlineLight = Color(0xFF74777F)
val outlineVariantLight = Color(0xFFC4C6CF)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF2E3035)
val inverseOnSurfaceLight = Color(0xFFF0F0F7)
val inversePrimaryLight = Color(0xFFA7C8FF)
val surfaceDimLight = Color(0xFFD9DAE0)
val surfaceBrightLight = Color(0xFFF9F9FF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF3F3FA)
val surfaceContainerLight = Color(0xFFEDEDF4)
val surfaceContainerHighLight = Color(0xFFE7E8EE)
val surfaceContainerHighestLight = Color(0xFFE1E2E9)

val primaryLightMediumContrast = Color(0xFF1F4372)
val onPrimaryLightMediumContrast = Color(0xFFFFFFFF)
val primaryContainerLightMediumContrast = Color(0xFF5476A8)
val onPrimaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val secondaryLightMediumContrast = Color(0xFF394354)
val onSecondaryLightMediumContrast = Color(0xFFFFFFFF)
val secondaryContainerLightMediumContrast = Color(0xFF6B7588)
val onSecondaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val tertiaryLightMediumContrast = Color(0xFF513B59)
val onTertiaryLightMediumContrast = Color(0xFFFFFFFF)
val tertiaryContainerLightMediumContrast = Color(0xFF856C8D)
val onTertiaryContainerLightMediumContrast = Color(0xFFFFFFFF)
val errorLightMediumContrast = Color(0xFF8C0009)
val onErrorLightMediumContrast = Color(0xFFFFFFFF)
val errorContainerLightMediumContrast = Color(0xFFDA342E)
val onErrorContainerLightMediumContrast = Color(0xFFFFFFFF)
val backgroundLightMediumContrast = Color(0xFFF9F9FF)
val onBackgroundLightMediumContrast = Color(0xFF191C20)
val surfaceLightMediumContrast = Color(0xFFF9F9FF)
val onSurfaceLightMediumContrast = Color(0xFF191C20)
val surfaceVariantLightMediumContrast = Color(0xFFE0E2EC)
val onSurfaceVariantLightMediumContrast = Color(0xFF3F434A)
val outlineLightMediumContrast = Color(0xFF5C5F67)
val outlineVariantLightMediumContrast = Color(0xFF777B83)
val scrimLightMediumContrast = Color(0xFF000000)
val inverseSurfaceLightMediumContrast = Color(0xFF2E3035)
val inverseOnSurfaceLightMediumContrast = Color(0xFFF0F0F7)
val inversePrimaryLightMediumContrast = Color(0xFFA7C8FF)
val surfaceDimLightMediumContrast = Color(0xFFD9DAE0)
val surfaceBrightLightMediumContrast = Color(0xFFF9F9FF)
val surfaceContainerLowestLightMediumContrast = Color(0xFFFFFFFF)
val surfaceContainerLowLightMediumContrast = Color(0xFFF3F3FA)
val surfaceContainerLightMediumContrast = Color(0xFFEDEDF4)
val surfaceContainerHighLightMediumContrast = Color(0xFFE7E8EE)
val surfaceContainerHighestLightMediumContrast = Color(0xFFE1E2E9)

val primaryLightHighContrast = Color(0xFF002247)
val onPrimaryLightHighContrast = Color(0xFFFFFFFF)
val primaryContainerLightHighContrast = Color(0xFF1F4372)
val onPrimaryContainerLightHighContrast = Color(0xFFFFFFFF)
val secondaryLightHighContrast = Color(0xFF192332)
val onSecondaryLightHighContrast = Color(0xFFFFFFFF)
val secondaryContainerLightHighContrast = Color(0xFF394354)
val onSecondaryContainerLightHighContrast = Color(0xFFFFFFFF)
val tertiaryLightHighContrast = Color(0xFF2E1A36)
val onTertiaryLightHighContrast = Color(0xFFFFFFFF)
val tertiaryContainerLightHighContrast = Color(0xFF513B59)
val onTertiaryContainerLightHighContrast = Color(0xFFFFFFFF)
val errorLightHighContrast = Color(0xFF4E0002)
val onErrorLightHighContrast = Color(0xFFFFFFFF)
val errorContainerLightHighContrast = Color(0xFF8C0009)
val onErrorContainerLightHighContrast = Color(0xFFFFFFFF)
val backgroundLightHighContrast = Color(0xFFF9F9FF)
val onBackgroundLightHighContrast = Color(0xFF191C20)
val surfaceLightHighContrast = Color(0xFFF9F9FF)
val onSurfaceLightHighContrast = Color(0xFF000000)
val surfaceVariantLightHighContrast = Color(0xFFE0E2EC)
val onSurfaceVariantLightHighContrast = Color(0xFF20242B)
val outlineLightHighContrast = Color(0xFF3F434A)
val outlineVariantLightHighContrast = Color(0xFF3F434A)
val scrimLightHighContrast = Color(0xFF000000)
val inverseSurfaceLightHighContrast = Color(0xFF2E3035)
val inverseOnSurfaceLightHighContrast = Color(0xFFFFFFFF)
val inversePrimaryLightHighContrast = Color(0xFFE4ECFF)
val surfaceDimLightHighContrast = Color(0xFFD9DAE0)
val surfaceBrightLightHighContrast = Color(0xFFF9F9FF)
val surfaceContainerLowestLightHighContrast = Color(0xFFFFFFFF)
val surfaceContainerLowLightHighContrast = Color(0xFFF3F3FA)
val surfaceContainerLightHighContrast = Color(0xFFEDEDF4)
val surfaceContainerHighLightHighContrast = Color(0xFFE7E8EE)
val surfaceContainerHighestLightHighContrast = Color(0xFFE1E2E9)

val primaryDark = Color(0xFFA7C8FF)
val onPrimaryDark = Color(0xFF03305F)
val primaryContainerDark = Color(0xFF234777)
val onPrimaryContainerDark = Color(0xFFD5E3FF)
val secondaryDark = Color(0xFFBDC7DC)
val onSecondaryDark = Color(0xFF273141)
val secondaryContainerDark = Color(0xFF3D4758)
val onSecondaryContainerDark = Color(0xFFD9E3F8)
val tertiaryDark = Color(0xFFDBBDE2)
val onTertiaryDark = Color(0xFF3E2845)
val tertiaryContainerDark = Color(0xFF553F5D)
val onTertiaryContainerDark = Color(0xFFF8D8FF)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF111318)
val onBackgroundDark = Color(0xFFE1E2E9)
val surfaceDark = Color(0xFF111318)
val onSurfaceDark = Color(0xFFE1E2E9)
val surfaceVariantDark = Color(0xFF43474E)
val onSurfaceVariantDark = Color(0xFFC4C6CF)
val outlineDark = Color(0xFF8D9199)
val outlineVariantDark = Color(0xFF43474E)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE1E2E9)
val inverseOnSurfaceDark = Color(0xFF2E3035)
val inversePrimaryDark = Color(0xFF3D5F90)
val surfaceDimDark = Color(0xFF111318)
val surfaceBrightDark = Color(0xFF37393E)
val surfaceContainerLowestDark = Color(0xFF0C0E13)
val surfaceContainerLowDark = Color(0xFF191C20)
val surfaceContainerDark = Color(0xFF1D2024)
val surfaceContainerHighDark = Color(0xFF282A2F)
val surfaceContainerHighestDark = Color(0xFF32353A)

val primaryDarkMediumContrast = Color(0xFFAECCFF)
val onPrimaryDarkMediumContrast = Color(0xFF001632)
val primaryContainerDarkMediumContrast = Color(0xFF7192C6)
val onPrimaryContainerDarkMediumContrast = Color(0xFF000000)
val secondaryDarkMediumContrast = Color(0xFFC1CBE0)
val onSecondaryDarkMediumContrast = Color(0xFF0C1726)
val secondaryContainerDarkMediumContrast = Color(0xFF8791A5)
val onSecondaryContainerDarkMediumContrast = Color(0xFF000000)
val tertiaryDarkMediumContrast = Color(0xFFDFC1E6)
val onTertiaryDarkMediumContrast = Color(0xFF220E2A)
val tertiaryContainerDarkMediumContrast = Color(0xFFA387AA)
val onTertiaryContainerDarkMediumContrast = Color(0xFF000000)
val errorDarkMediumContrast = Color(0xFFFFBAB1)
val onErrorDarkMediumContrast = Color(0xFF370001)
val errorContainerDarkMediumContrast = Color(0xFFFF5449)
val onErrorContainerDarkMediumContrast = Color(0xFF000000)
val backgroundDarkMediumContrast = Color(0xFF111318)
val onBackgroundDarkMediumContrast = Color(0xFFE1E2E9)
val surfaceDarkMediumContrast = Color(0xFF111318)
val onSurfaceDarkMediumContrast = Color(0xFFFBFAFF)
val surfaceVariantDarkMediumContrast = Color(0xFF43474E)
val onSurfaceVariantDarkMediumContrast = Color(0xFFC8CAD4)
val outlineDarkMediumContrast = Color(0xFFA0A3AB)
val outlineVariantDarkMediumContrast = Color(0xFF80838B)
val scrimDarkMediumContrast = Color(0xFF000000)
val inverseSurfaceDarkMediumContrast = Color(0xFFE1E2E9)
val inverseOnSurfaceDarkMediumContrast = Color(0xFF282A2F)
val inversePrimaryDarkMediumContrast = Color(0xFF254978)
val surfaceDimDarkMediumContrast = Color(0xFF111318)
val surfaceBrightDarkMediumContrast = Color(0xFF37393E)
val surfaceContainerLowestDarkMediumContrast = Color(0xFF0C0E13)
val surfaceContainerLowDarkMediumContrast = Color(0xFF191C20)
val surfaceContainerDarkMediumContrast = Color(0xFF1D2024)
val surfaceContainerHighDarkMediumContrast = Color(0xFF282A2F)
val surfaceContainerHighestDarkMediumContrast = Color(0xFF32353A)

val primaryDarkHighContrast = Color(0xFFFBFAFF)
val onPrimaryDarkHighContrast = Color(0xFF000000)
val primaryContainerDarkHighContrast = Color(0xFFAECCFF)
val onPrimaryContainerDarkHighContrast = Color(0xFF000000)
val secondaryDarkHighContrast = Color(0xFFFBFAFF)
val onSecondaryDarkHighContrast = Color(0xFF000000)
val secondaryContainerDarkHighContrast = Color(0xFFC1CBE0)
val onSecondaryContainerDarkHighContrast = Color(0xFF000000)
val tertiaryDarkHighContrast = Color(0xFFFFF9FA)
val onTertiaryDarkHighContrast = Color(0xFF000000)
val tertiaryContainerDarkHighContrast = Color(0xFFDFC1E6)
val onTertiaryContainerDarkHighContrast = Color(0xFF000000)
val errorDarkHighContrast = Color(0xFFFFF9F9)
val onErrorDarkHighContrast = Color(0xFF000000)
val errorContainerDarkHighContrast = Color(0xFFFFBAB1)
val onErrorContainerDarkHighContrast = Color(0xFF000000)
val backgroundDarkHighContrast = Color(0xFF111318)
val onBackgroundDarkHighContrast = Color(0xFFE1E2E9)
val surfaceDarkHighContrast = Color(0xFF111318)
val onSurfaceDarkHighContrast = Color(0xFFFFFFFF)
val surfaceVariantDarkHighContrast = Color(0xFF43474E)
val onSurfaceVariantDarkHighContrast = Color(0xFFFBFAFF)
val outlineDarkHighContrast = Color(0xFFC8CAD4)
val outlineVariantDarkHighContrast = Color(0xFFC8CAD4)
val scrimDarkHighContrast = Color(0xFF000000)
val inverseSurfaceDarkHighContrast = Color(0xFFE1E2E9)
val inverseOnSurfaceDarkHighContrast = Color(0xFF000000)
val inversePrimaryDarkHighContrast = Color(0xFF002A55)
val surfaceDimDarkHighContrast = Color(0xFF111318)
val surfaceBrightDarkHighContrast = Color(0xFF37393E)
val surfaceContainerLowestDarkHighContrast = Color(0xFF0C0E13)
val surfaceContainerLowDarkHighContrast = Color(0xFF191C20)
val surfaceContainerDarkHighContrast = Color(0xFF1D2024)
val surfaceContainerHighDarkHighContrast = Color(0xFF282A2F)
val surfaceContainerHighestDarkHighContrast = Color(0xFF32353A)

val rightLight = Color(0xFF2C6A46)
val onRightLight = Color(0xFFFFFFFF)
val rightContainerLight = Color(0xFFB0F1C3)
val onRightContainerLight = Color(0xFF00210F)
val wrongLight = Color(0xFF8F4B39)
val onWrongLight = Color(0xFFFFFFFF)
val wrongContainerLight = Color(0xFFFFDBD2)
val onWrongContainerLight = Color(0xFF3A0A01)

val rightLightMediumContrast = Color(0xFF064D2C)
val onRightLightMediumContrast = Color(0xFFFFFFFF)
val rightContainerLightMediumContrast = Color(0xFF43815B)
val onRightContainerLightMediumContrast = Color(0xFFFFFFFF)
val wrongLightMediumContrast = Color(0xFF6D3121)
val onWrongLightMediumContrast = Color(0xFFFFFFFF)
val wrongContainerLightMediumContrast = Color(0xFFAA614D)
val onWrongContainerLightMediumContrast = Color(0xFFFFFFFF)

val rightLightHighContrast = Color(0xFF002914)
val onRightLightHighContrast = Color(0xFFFFFFFF)
val rightContainerLightHighContrast = Color(0xFF064D2C)
val onRightContainerLightHighContrast = Color(0xFFFFFFFF)
val wrongLightHighContrast = Color(0xFF431105)
val onWrongLightHighContrast = Color(0xFFFFFFFF)
val wrongContainerLightHighContrast = Color(0xFF6D3121)
val onWrongContainerLightHighContrast = Color(0xFFFFFFFF)

val rightDark = Color(0xFF94D5A8)
val onRightDark = Color(0xFF00391E)
val rightContainerDark = Color(0xFF0E5130)
val onRightContainerDark = Color(0xFFB0F1C3)
val wrongDark = Color(0xFFFFB4A1)
val onWrongDark = Color(0xFF561F10)
val wrongContainerDark = Color(0xFF723524)
val onWrongContainerDark = Color(0xFFFFDBD2)

val rightDarkMediumContrast = Color(0xFF98D9AC)
val onRightDarkMediumContrast = Color(0xFF001B0C)
val rightContainerDarkMediumContrast = Color(0xFF5F9E75)
val onRightContainerDarkMediumContrast = Color(0xFF000000)
val wrongDarkMediumContrast = Color(0xFFFFBAA9)
val onWrongDarkMediumContrast = Color(0xFF320600)
val wrongContainerDarkMediumContrast = Color(0xFFCB7C67)
val onWrongContainerDarkMediumContrast = Color(0xFF000000)

val rightDarkHighContrast = Color(0xFFEFFFF0)
val onRightDarkHighContrast = Color(0xFF000000)
val rightContainerDarkHighContrast = Color(0xFF98D9AC)
val onRightContainerDarkHighContrast = Color(0xFF000000)
val wrongDarkHighContrast = Color(0xFFFFF9F8)
val onWrongDarkHighContrast = Color(0xFF000000)
val wrongContainerDarkHighContrast = Color(0xFFFFBAA9)
val onWrongContainerDarkHighContrast = Color(0xFF000000)



@Immutable
data class ExtendedColorScheme(
    val right: ColorFamily,
    val wrong: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    right = ColorFamily(
        rightLight,
        onRightLight,
        rightContainerLight,
        onRightContainerLight,
    ),
    wrong = ColorFamily(
        wrongLight,
        onWrongLight,
        wrongContainerLight,
        onWrongContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    right = ColorFamily(
        rightDark,
        onRightDark,
        rightContainerDark,
        onRightContainerDark,
    ),
    wrong = ColorFamily(
        wrongDark,
        onWrongDark,
        wrongContainerDark,
        onWrongContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    right = ColorFamily(
        rightLightMediumContrast,
        onRightLightMediumContrast,
        rightContainerLightMediumContrast,
        onRightContainerLightMediumContrast,
    ),
    wrong = ColorFamily(
        wrongLightMediumContrast,
        onWrongLightMediumContrast,
        wrongContainerLightMediumContrast,
        onWrongContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    right = ColorFamily(
        rightLightHighContrast,
        onRightLightHighContrast,
        rightContainerLightHighContrast,
        onRightContainerLightHighContrast,
    ),
    wrong = ColorFamily(
        wrongLightHighContrast,
        onWrongLightHighContrast,
        wrongContainerLightHighContrast,
        onWrongContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    right = ColorFamily(
        rightDarkMediumContrast,
        onRightDarkMediumContrast,
        rightContainerDarkMediumContrast,
        onRightContainerDarkMediumContrast,
    ),
    wrong = ColorFamily(
        wrongDarkMediumContrast,
        onWrongDarkMediumContrast,
        wrongContainerDarkMediumContrast,
        onWrongContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    right = ColorFamily(
        rightDarkHighContrast,
        onRightDarkHighContrast,
        rightContainerDarkHighContrast,
        onRightContainerDarkHighContrast,
    ),
    wrong = ColorFamily(
        wrongDarkHighContrast,
        onWrongDarkHighContrast,
        wrongContainerDarkHighContrast,
        onWrongContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)


val LightAndroidGradientColors = GradientColors(container = primaryContainerLight)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = primaryContainerDark)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)
