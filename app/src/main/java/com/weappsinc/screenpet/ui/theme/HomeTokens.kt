package com.weappsinc.screenpet.ui.theme

import androidx.compose.ui.graphics.Color

/** Token mau cho man Home + bottom nav (Shimeji live). */
object HomeTokens {
    val NavActive: Color = Color(0xFFEE4096)
    val NavInactive: Color = Color(0xFFA6A6A6)
    val Pink: Color = Color(0xFFFFAED6)
    val LockIcon: Color = Color(0xFFC58B22)
    val LockTile: Color = Color(0xFFFFF5E6)
    val CardSurface: Color = Color(0xFFFFFFFF)
    val Background: Color = Color(0xFFF7F2FA)
    val SwitchOffTrack: Color = Color(0xFFE0E0E0)

    val ActivateGradient: List<Color> = listOf(
        SplashTokens.TitleGradientTop,
        SplashTokens.TitleGradientBottom,
    )
}
