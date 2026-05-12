package com.weappsinc.screenpet.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Token mau cho man Home + bottom nav (Shimeji live). */
object HomeTokens {
    /** Ten pet trong o slot: neu dai hon se rut gon (chu cai dau / ...). */
    const val SlotPetNameMaxDisplayChars: Int = 14

    val NavActive: Color = Color(0xFFEE4096)
    val NavInactive: Color = Color(0xFFA6A6A6)
    val Pink: Color = Color(0xFFFFAED6)
    /** Vong tron nen dau + trong o trong. */
    val SlotEmptyCircleFill: Color = Color(0xFFFFF0F7)
    /** Vien net dut o slot trong: hong lavender nhat. */
    val SlotEmptyDashBorder: Color = Color(0xFFFFB3D9)
    /** Vien lien o slot khoa: xam xanh nhat. */
    val SlotLockedBorder: Color = Color(0xFFC9CDD5)
    /** Nen card o slot khoa (tim xam nhat) de noi bat vong tron trang. */
    val SlotUnlockCardSurface: Color = Color(0xFFE5E5F3)
    /** Chu tren card Unlock (tim dam, dam bao mockup). */
    val SlotUnlockCardTitle: Color = Color(0xFF4A3F6B)
    /** Do bong card Unlock (ro hon de thay tren nen scroll). */
    val SlotUnlockCardElevationDp: Dp = 9.dp
    val SlotUnlockCardShadowAmbient: Color = Color(0x30000000)
    val SlotUnlockCardShadowSpot: Color = Color(0x45000000)
    /** Vong tron trang quanh icon khoa. */
    val SlotUnlockIconRingElevationDp: Dp = 2.dp
    val SlotUnlockIconRingShadowAmbient: Color = Color(0x12000000)
    val SlotUnlockIconRingShadowSpot: Color = Color(0x1F000000)
    /** Chu "Unlock" duoi khoa: xam xanh. */
    val SlotLockedLabel: Color = Color(0xFF6B7A8F)
    val LockIcon: Color = Color(0xFFFF8C42)
    val LockTile: Color = Color(0xFFFFF5E6)
    val CardSurface: Color = Color(0xFFFFFFFF)
    val Background: Color = Color(0xFFF7F2FA)
    /** Track switch tat: xam tim mo (#E1DFEE). */
    val SwitchOffTrack: Color = Color(0xFFE1DFEE)
    /** Track switch bat (true): hong #EF4094. */
    val SwitchCheckedTrack: Color = Color(0xFFEF4094)
    /** Tim — icon Swarm, chevron Mix, v.v. */
    val SwitchPurple: Color = Color(0xFF8152FF)
    val SwarmTitle: Color = Color(0xFF4A3F6B)

    /** Nen gradient card Kich hoat Shimeji (trai -> phai). */
    val ActivateGradient: List<Color> = listOf(
        Color(0xFFEF4094),
        Color(0xFF8152FF),
    )
}
