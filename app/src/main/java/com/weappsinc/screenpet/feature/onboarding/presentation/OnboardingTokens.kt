package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Token UI rieng cho man Onboarding (theo mockup). */
object OnboardingTokens {
    val ProgressActive: Color = Color(0xFFFFFFFF)
    val ProgressInactive: Color = Color(0x66FFFFFF)
    val NextLabel: Color = Color(0xFFFFFFFF)
    val CaptionText: Color = Color(0xFF1B1B1F)

    val PageCount: Int = 3

    val TopBarHeight: Dp = 56.dp
    val ProgressBarHeight: Dp = 4.dp
    val ProgressBarSegmentWidth: Dp = 22.dp
    val ProgressBarGap: Dp = 6.dp
    val CaptionPanelHeight: Dp = 140.dp
    val PetBobAmplitudeDp: Dp = 4.dp
    val PetBobPeriodMs: Int = 1600
}
