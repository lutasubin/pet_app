package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.weappsinc.screenpet.ui.theme.HomeTokens

/** Mau switch Home: bat #8152FF, tat #E1DFEE (Kich hoat + Pet Swarm). */
@Composable
fun homeToggleSwitchColors(): SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = Color.White,
    checkedTrackColor = HomeTokens.SwitchPurple,
    uncheckedThumbColor = Color.White,
    uncheckedTrackColor = HomeTokens.SwitchOffTrack,
    checkedBorderColor = Color.Transparent,
    uncheckedBorderColor = Color.Transparent,
)
