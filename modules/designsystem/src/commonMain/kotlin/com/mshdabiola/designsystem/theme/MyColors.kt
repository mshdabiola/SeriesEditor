package com.mshdabiola.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

sealed class MyColors {

    abstract val primaryLight: Color
    abstract val onPrimaryLight: Color
    abstract val primaryContainerLight: Color
    abstract val onPrimaryContainerLight: Color
    abstract val secondaryLight: Color
    abstract val onSecondaryLight: Color
    abstract val secondaryContainerLight: Color
    abstract val onSecondaryContainerLight: Color
    abstract val tertiaryLight: Color
    abstract val onTertiaryLight: Color
    abstract val tertiaryContainerLight: Color
    abstract val onTertiaryContainerLight: Color
    abstract val errorLight: Color
    abstract val onErrorLight: Color
    abstract val errorContainerLight: Color
    abstract val onErrorContainerLight: Color
    abstract val backgroundLight: Color
    abstract val onBackgroundLight: Color
    abstract val surfaceLight: Color
    abstract val onSurfaceLight: Color
    abstract val surfaceVariantLight: Color
    abstract val onSurfaceVariantLight: Color
    abstract val outlineLight: Color
    abstract val outlineVariantLight: Color
    abstract val scrimLight: Color
    abstract val inverseSurfaceLight: Color
    abstract val inverseOnSurfaceLight: Color
    abstract val inversePrimaryLight: Color
    abstract val surfaceDimLight: Color
    abstract val surfaceBrightLight: Color
    abstract val surfaceContainerLowestLight: Color
    abstract val surfaceContainerLowLight: Color
    abstract val surfaceContainerLight: Color
    abstract val surfaceContainerHighLight: Color
    abstract val surfaceContainerHighestLight: Color

    abstract val primaryDark: Color
    abstract val onPrimaryDark: Color
    abstract val primaryContainerDark: Color
    abstract val onPrimaryContainerDark: Color
    abstract val secondaryDark: Color
    abstract val onSecondaryDark: Color
    abstract val secondaryContainerDark: Color
    abstract val onSecondaryContainerDark: Color
    abstract val tertiaryDark: Color
    abstract val onTertiaryDark: Color
    abstract val tertiaryContainerDark: Color
    abstract val onTertiaryContainerDark: Color
    abstract val errorDark: Color
    abstract val onErrorDark: Color
    abstract val errorContainerDark: Color
    abstract val onErrorContainerDark: Color
    abstract val backgroundDark: Color
    abstract val onBackgroundDark: Color
    abstract val surfaceDark: Color
    abstract val onSurfaceDark: Color
    abstract val surfaceVariantDark: Color
    abstract val onSurfaceVariantDark: Color
    abstract val outlineDark: Color
    abstract val outlineVariantDark: Color
    abstract val scrimDark: Color
    abstract val inverseSurfaceDark: Color
    abstract val inverseOnSurfaceDark: Color
    abstract val inversePrimaryDark: Color
    abstract val surfaceDimDark: Color
    abstract val surfaceBrightDark: Color
    abstract val surfaceContainerLowestDark: Color
    abstract val surfaceContainerLowDark: Color
    abstract val surfaceContainerDark: Color
    abstract val surfaceContainerHighDark: Color
    abstract val surfaceContainerHighestDark: Color

    data object Brown : MyColors() {

        override val primaryLight = Color(0xFF8E4D31)
        override val onPrimaryLight = Color(0xFFFFFFFF)
        override val primaryContainerLight = Color(0xFFFFDBCE)
        override val onPrimaryContainerLight = Color(0xFF370E00)
        override val secondaryLight = Color(0xFF77574B)
        override val onSecondaryLight = Color(0xFFFFFFFF)
        override val secondaryContainerLight = Color(0xFFFFDBCE)
        override val onSecondaryContainerLight = Color(0xFF2C160C)
        override val tertiaryLight = Color(0xFF685F30)
        override val onTertiaryLight = Color(0xFFFFFFFF)
        override val tertiaryContainerLight = Color(0xFFF0E3A8)
        override val onTertiaryContainerLight = Color(0xFF211B00)
        override val errorLight = Color(0xFFBA1A1A)
        override val onErrorLight = Color(0xFFFFFFFF)
        override val errorContainerLight = Color(0xFFFFDAD6)
        override val onErrorContainerLight = Color(0xFF410002)
        override val backgroundLight = Color(0xFFFFF8F6)
        override val onBackgroundLight = Color(0xFF231A16)
        override val surfaceLight = Color(0xFFFFF8F6)
        override val onSurfaceLight = Color(0xFF231A16)
        override val surfaceVariantLight = Color(0xFFF5DED6)
        override val onSurfaceVariantLight = Color(0xFF53433E)
        override val outlineLight = Color(0xFF85736D)
        override val outlineVariantLight = Color(0xFFD8C2BA)
        override val scrimLight = Color(0xFF000000)
        override val inverseSurfaceLight = Color(0xFF382E2A)
        override val inverseOnSurfaceLight = Color(0xFFFFEDE7)
        override val inversePrimaryLight = Color(0xFFFFB598)
        override val surfaceDimLight = Color(0xFFE8D6D0)
        override val surfaceBrightLight = Color(0xFFFFF8F6)
        override val surfaceContainerLowestLight = Color(0xFFFFFFFF)
        override val surfaceContainerLowLight = Color(0xFFFFF1EC)
        override val surfaceContainerLight = Color(0xFFFCEAE4)
        override val surfaceContainerHighLight = Color(0xFFF6E4DE)
        override val surfaceContainerHighestLight = Color(0xFFF1DFD9)

        override val primaryDark = Color(0xFFFFB598)
        override val onPrimaryDark = Color(0xFF552008)
        override val primaryContainerDark = Color(0xFF71361C)
        override val onPrimaryContainerDark = Color(0xFFFFDBCE)
        override val secondaryDark = Color(0xFFE7BEAE)
        override val onSecondaryDark = Color(0xFF442A20)
        override val secondaryContainerDark = Color(0xFF5D4035)
        override val onSecondaryContainerDark = Color(0xFFFFDBCE)
        override val tertiaryDark = Color(0xFFD3C78E)
        override val onTertiaryDark = Color(0xFF383006)
        override val tertiaryContainerDark = Color(0xFF4F471B)
        override val onTertiaryContainerDark = Color(0xFFF0E3A8)
        override val errorDark = Color(0xFFFFB4AB)
        override val onErrorDark = Color(0xFF690005)
        override val errorContainerDark = Color(0xFF93000A)
        override val onErrorContainerDark = Color(0xFFFFDAD6)
        override val backgroundDark = Color(0xFF1A110E)
        override val onBackgroundDark = Color(0xFFF1DFD9)
        override val surfaceDark = Color(0xFF1A110E)
        override val onSurfaceDark = Color(0xFFF1DFD9)
        override val surfaceVariantDark = Color(0xFF53433E)
        override val onSurfaceVariantDark = Color(0xFFD8C2BA)
        override val outlineDark = Color(0xFFA08D86)
        override val outlineVariantDark = Color(0xFF53433E)
        override val scrimDark = Color(0xFF000000)
        override val inverseSurfaceDark = Color(0xFFF1DFD9)
        override val inverseOnSurfaceDark = Color(0xFF382E2A)
        override val inversePrimaryDark = Color(0xFF8E4D31)
        override val surfaceDimDark = Color(0xFF1A110E)
        override val surfaceBrightDark = Color(0xFF423733)
        override val surfaceContainerLowestDark = Color(0xFF140C09)
        override val surfaceContainerLowDark = Color(0xFF231A16)
        override val surfaceContainerDark = Color(0xFF271E1A)
        override val surfaceContainerHighDark = Color(0xFF322824)
        override val surfaceContainerHighestDark = Color(0xFF3D322F)
    }

    data object Default : MyColors() {

        override val primaryLight = Color(0xFF3D5F90)
        override val onPrimaryLight = Color(0xFFFFFFFF)
        override val primaryContainerLight = Color(0xFFD5E3FF)
        override val onPrimaryContainerLight = Color(0xFF001C3B)
        override val secondaryLight = Color(0xFF555F71)
        override val onSecondaryLight = Color(0xFFFFFFFF)
        override val secondaryContainerLight = Color(0xFFD9E3F8)
        override val onSecondaryContainerLight = Color(0xFF121C2B)
        override val tertiaryLight = Color(0xFF6E5676)
        override val onTertiaryLight = Color(0xFFFFFFFF)
        override val tertiaryContainerLight = Color(0xFFF8D8FF)
        override val onTertiaryContainerLight = Color(0xFF27132F)
        override val errorLight = Color(0xFFBA1A1A)
        override val onErrorLight = Color(0xFFFFFFFF)
        override val errorContainerLight = Color(0xFFFFDAD6)
        override val onErrorContainerLight = Color(0xFF410002)
        override val backgroundLight = Color(0xFFF9F9FF)
        override val onBackgroundLight = Color(0xFF191C20)
        override val surfaceLight = Color(0xFFF9F9FF)
        override val onSurfaceLight = Color(0xFF191C20)
        override val surfaceVariantLight = Color(0xFFE0E2EC)
        override val onSurfaceVariantLight = Color(0xFF43474E)
        override val outlineLight = Color(0xFF74777F)
        override val outlineVariantLight = Color(0xFFC4C6CF)
        override val scrimLight = Color(0xFF000000)
        override val inverseSurfaceLight = Color(0xFF2E3035)
        override val inverseOnSurfaceLight = Color(0xFFF0F0F7)
        override val inversePrimaryLight = Color(0xFFA7C8FF)
        override val surfaceDimLight = Color(0xFFD9DAE0)
        override val surfaceBrightLight = Color(0xFFF9F9FF)
        override val surfaceContainerLowestLight = Color(0xFFFFFFFF)
        override val surfaceContainerLowLight = Color(0xFFF3F3FA)
        override val surfaceContainerLight = Color(0xFFEDEDF4)
        override val surfaceContainerHighLight = Color(0xFFE7E8EE)
        override val surfaceContainerHighestLight = Color(0xFFE1E2E9)

        override val primaryDark = Color(0xFFA7C8FF)
        override val onPrimaryDark = Color(0xFF03305F)
        override val primaryContainerDark = Color(0xFF234777)
        override val onPrimaryContainerDark = Color(0xFFD5E3FF)
        override val secondaryDark = Color(0xFFBDC7DC)
        override val onSecondaryDark = Color(0xFF273141)
        override val secondaryContainerDark = Color(0xFF3D4758)
        override val onSecondaryContainerDark = Color(0xFFD9E3F8)
        override val tertiaryDark = Color(0xFFDBBDE2)
        override val onTertiaryDark = Color(0xFF3E2845)
        override val tertiaryContainerDark = Color(0xFF553F5D)
        override val onTertiaryContainerDark = Color(0xFFF8D8FF)
        override val errorDark = Color(0xFFFFB4AB)
        override val onErrorDark = Color(0xFF690005)
        override val errorContainerDark = Color(0xFF93000A)
        override val onErrorContainerDark = Color(0xFFFFDAD6)
        override val backgroundDark = Color(0xFF111318)
        override val onBackgroundDark = Color(0xFFE1E2E9)
        override val surfaceDark = Color(0xFF111318)
        override val onSurfaceDark = Color(0xFFE1E2E9)
        override val surfaceVariantDark = Color(0xFF43474E)
        override val onSurfaceVariantDark = Color(0xFFC4C6CF)
        override val outlineDark = Color(0xFF8D9199)
        override val outlineVariantDark = Color(0xFF43474E)
        override val scrimDark = Color(0xFF000000)
        override val inverseSurfaceDark = Color(0xFFE1E2E9)
        override val inverseOnSurfaceDark = Color(0xFF2E3035)
        override val inversePrimaryDark = Color(0xFF3D5F90)
        override val surfaceDimDark = Color(0xFF111318)
        override val surfaceBrightDark = Color(0xFF37393E)
        override val surfaceContainerLowestDark = Color(0xFF0C0E13)
        override val surfaceContainerLowDark = Color(0xFF191C20)
        override val surfaceContainerDark = Color(0xFF1D2024)
        override val surfaceContainerHighDark = Color(0xFF282A2F)
        override val surfaceContainerHighestDark = Color(0xFF32353A)
    }
}

val rightLight = Color(0xFF2C6A46)
val onRightLight = Color(0xFFFFFFFF)
val rightContainerLight = Color(0xFFB0F1C3)
val onRightContainerLight = Color(0xFF00210F)
val wrongLight = Color(0xFF8F4B39)
val onWrongLight = Color(0xFFFFFFFF)
val wrongContainerLight = Color(0xFFFFDBD2)
val onWrongContainerLight = Color(0xFF3A0A01)

val rightDark = Color(0xFF94D5A8)
val onRightDark = Color(0xFF00391E)
val rightContainerDark = Color(0xFF0E5130)
val onRightContainerDark = Color(0xFFB0F1C3)
val wrongDark = Color(0xFFFFB4A1)
val onWrongDark = Color(0xFF561F10)
val wrongContainerDark = Color(0xFF723524)
val onWrongContainerDark = Color(0xFFFFDBD2)

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

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
)

@Immutable
data class ExtendedColorScheme(
    val right: ColorFamily,
    val wrong: ColorFamily,
)
