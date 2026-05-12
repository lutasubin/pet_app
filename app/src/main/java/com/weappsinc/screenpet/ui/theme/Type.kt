package com.weappsinc.screenpet.ui.theme

import androidx.compose.material3.Typography

private val DefaultTypography = Typography()

/** Typography Material 3 voi font Nunito cho toan app. */
val Typography: Typography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(fontFamily = NunitoFontFamily),
    displayMedium = DefaultTypography.displayMedium.copy(fontFamily = NunitoFontFamily),
    displaySmall = DefaultTypography.displaySmall.copy(fontFamily = NunitoFontFamily),
    headlineLarge = DefaultTypography.headlineLarge.copy(fontFamily = NunitoFontFamily),
    headlineMedium = DefaultTypography.headlineMedium.copy(fontFamily = NunitoFontFamily),
    headlineSmall = DefaultTypography.headlineSmall.copy(fontFamily = NunitoFontFamily),
    titleLarge = DefaultTypography.titleLarge.copy(fontFamily = NunitoFontFamily),
    titleMedium = DefaultTypography.titleMedium.copy(fontFamily = NunitoFontFamily),
    titleSmall = DefaultTypography.titleSmall.copy(fontFamily = NunitoFontFamily),
    bodyLarge = DefaultTypography.bodyLarge.copy(fontFamily = NunitoFontFamily),
    bodyMedium = DefaultTypography.bodyMedium.copy(fontFamily = NunitoFontFamily),
    bodySmall = DefaultTypography.bodySmall.copy(fontFamily = NunitoFontFamily),
    labelLarge = DefaultTypography.labelLarge.copy(fontFamily = NunitoFontFamily),
    labelMedium = DefaultTypography.labelMedium.copy(fontFamily = NunitoFontFamily),
    labelSmall = DefaultTypography.labelSmall.copy(fontFamily = NunitoFontFamily),
)
