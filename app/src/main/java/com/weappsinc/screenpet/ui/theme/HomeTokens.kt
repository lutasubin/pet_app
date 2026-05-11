package com.weappsinc.screenpet.ui.theme

import androidx.compose.ui.graphics.Color

/** Token mau cho man Home + bottom nav (Shimeji live). */
object HomeTokens {
    val NavActive: Color = Color(0xFFEE4096)
    val NavInactive: Color = Color(0xFFA6A6A6)
    val Pink: Color = Color(0xFFFFAED6)
    /** Vien net dut o slot trong (anh 1/2): hong lavender nhat. */
    val SlotEmptyDashBorder: Color = Color(0xFFFFB3D9)
    /** Vong tron nen dau + trong o trong. */
    val SlotEmptyCircleFill: Color = Color(0xFFFFF0F7)
    /** Vien lien o slot khoa: xam xanh nhat. */
    val SlotLockedBorder: Color = Color(0xFFC9CDD5)
    /** Chu "Unlock" duoi khoa: xam xanh. */
    val SlotLockedLabel: Color = Color(0xFF6B7A8F)
    val LockIcon: Color = Color(0xFFFF8C42)
    val LockTile: Color = Color(0xFFFFF5E6)
    val CardSurface: Color = Color(0xFFFFFFFF)
    val Background: Color = Color(0xFFF7F2FA)
    /** Track switch tat: xam tim mo (#E1DFEE). */
    val SwitchOffTrack: Color = Color(0xFFE1DFEE)
    /** Track switch bat (Kich hoat + Swarm). */
    val SwitchPurple: Color = Color(0xFF8152FF)
    val SwarmTitle: Color = Color(0xFF4A3F6B)

    /** Nen gradient card Kich hoat Shimeji (trai -> phai). */
    val ActivateGradient: List<Color> = listOf(
        Color(0xFFEF4094),
        Color(0xFF8152FF),
    )
}
