package com.weappsinc.screenpet.core.constants

/**
 * Thu tu demo: moi muc la mot HANH DONG (clip Shimeji), ben trong dung nhieu frame trong 46 anh.
 * Khong quet shime1..46 tuan tu — gom frame thanh cu chi that hon.
 */
object PetDemoActionPlaylist {

    val entries: List<Pair<ShimejiClipId, Long>> = listOf(
        ShimejiClipId.Stand to 900L,
        ShimejiClipId.Walk to 2800L,
        ShimejiClipId.Run to 2200L,
        ShimejiClipId.WalkSimple to 2400L,
        ShimejiClipId.Dash to 2000L,
        ShimejiClipId.Sit to 2200L,
        ShimejiClipId.SitSpinHead to 2600L,
        ShimejiClipId.SitDangleLegs to 2800L,
        ShimejiClipId.SitLegsUp to 1800L,
        ShimejiClipId.Sprawl to 2000L,
        ShimejiClipId.Jumping to 2000L,
        ShimejiClipId.Falling to 2200L,
        ShimejiClipId.Tripping to 2600L,
        ShimejiClipId.Bouncing to 2400L,
        ShimejiClipId.GrabWall to 1400L,
        ShimejiClipId.ClimbWall to 3200L,
        ShimejiClipId.GrabCeiling to 1400L,
        ShimejiClipId.ClimbCeiling to 3200L,
        ShimejiClipId.Creep to 2600L,
        ShimejiClipId.Resisting to 2400L,
        ShimejiClipId.PinchDragPreview to 2800L,
        ShimejiClipId.WalkWithIe to 2600L,
        ShimejiClipId.RunWithIe to 2000L,
        ShimejiClipId.ThrowIe to 1800L,
        ShimejiClipId.PullUpSpawn to 3400L,
        ShimejiClipId.PullUpFlung to 1600L,
        ShimejiClipId.Divide to 3000L,
        ShimejiClipId.FallWithIe to 2000L,
    )
}
