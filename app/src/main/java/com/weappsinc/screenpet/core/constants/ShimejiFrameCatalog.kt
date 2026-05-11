package com.weappsinc.screenpet.core.constants

/**
 * Bang clip frame trich tu Shimeji-EE (logany20/shimeji-ee conf/actions.xml).
 * Dung lam single source of truth cho thu tu khung anh.
 */
object ShimejiFrameCatalog {

    private val resistingFrames: List<Int> = listOf(
        5, 6, 5, 6, 1, 5, 6, 5, 6, 5, 6, 5, 6, 1,
        5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6,
    )

    private val clips: Map<ShimejiClipId, PetFrameClip> = mapOf(
        ShimejiClipId.Stand to PetFrameClip(listOf(1), loop = false, defaultFrameMs = 250),
        ShimejiClipId.Walk to PetFrameClip(listOf(1, 2, 1, 3), loop = true, defaultFrameMs = 6),
        ShimejiClipId.WalkSimple to PetFrameClip(listOf(1, 2, 3), loop = true, defaultFrameMs = 6),
        ShimejiClipId.Run to PetFrameClip(listOf(1, 2, 1, 3), loop = true, defaultFrameMs = 2),
        ShimejiClipId.Dash to PetFrameClip(listOf(1, 2, 1, 3), loop = true, defaultFrameMs = 2),
        ShimejiClipId.Sit to PetFrameClip(listOf(11), loop = false, defaultFrameMs = 250),
        ShimejiClipId.SitLookUp to PetFrameClip(listOf(26), loop = false, defaultFrameMs = 250),
        ShimejiClipId.SitSpinHead to PetFrameClip(
            listOf(26, 15, 27, 16, 28, 17, 29, 11),
            loop = false,
            defaultFrameMs = 5,
        ),
        ShimejiClipId.SitLegsUp to PetFrameClip(listOf(30), loop = false, defaultFrameMs = 250),
        ShimejiClipId.SitLegsDown to PetFrameClip(listOf(31), loop = false, defaultFrameMs = 250),
        ShimejiClipId.SitDangleLegs to PetFrameClip(
            listOf(31, 32, 31, 33),
            loop = true,
            defaultFrameMs = 10,
        ),
        ShimejiClipId.Sprawl to PetFrameClip(listOf(21), loop = false, defaultFrameMs = 250),
        ShimejiClipId.Creep to PetFrameClip(listOf(20, 20, 21, 21, 21), loop = false, defaultFrameMs = 8),
        ShimejiClipId.GrabCeiling to PetFrameClip(listOf(23), loop = false, defaultFrameMs = 250),
        ShimejiClipId.ClimbCeiling to PetFrameClip(
            listOf(25, 25, 23, 24, 24, 24, 23, 25),
            loop = true,
            defaultFrameMs = 8,
        ),
        ShimejiClipId.GrabWall to PetFrameClip(listOf(13), loop = false, defaultFrameMs = 250),
        ShimejiClipId.ClimbWall to PetFrameClip(
            listOf(14, 14, 12, 13, 13, 13, 12, 14),
            loop = true,
            defaultFrameMs = 8,
        ),
        ShimejiClipId.Falling to PetFrameClip(listOf(4), loop = false, defaultFrameMs = 250),
        ShimejiClipId.Bouncing to PetFrameClip(listOf(18, 19), loop = true, defaultFrameMs = 4),
        ShimejiClipId.Tripping to PetFrameClip(
            listOf(19, 18, 20, 20, 19),
            loop = false,
            defaultFrameMs = 6,
        ),
        ShimejiClipId.Jumping to PetFrameClip(listOf(22), loop = false, defaultFrameMs = 250),
        ShimejiClipId.PinchDragPreview to PetFrameClip(
            listOf(9, 7, 1, 8, 10),
            loop = true,
            defaultFrameMs = 5,
        ),
        ShimejiClipId.Resisting to PetFrameClip(resistingFrames, loop = true, defaultFrameMs = 5),
        ShimejiClipId.FallWithIe to PetFrameClip(listOf(36), loop = false, defaultFrameMs = 250),
        ShimejiClipId.WalkWithIe to PetFrameClip(listOf(34, 35, 34, 36), loop = true, defaultFrameMs = 6),
        ShimejiClipId.RunWithIe to PetFrameClip(listOf(34, 35, 34, 36), loop = true, defaultFrameMs = 2),
        ShimejiClipId.ThrowIe to PetFrameClip(listOf(37), loop = false, defaultFrameMs = 40),
        ShimejiClipId.PullUpSpawn to PetFrameClip(
            listOf(1, 38, 39, 40, 41),
            loop = false,
            defaultFrameMs = 20,
        ),
        ShimejiClipId.PullUpFlung to PetFrameClip(listOf(9, 9, 9), loop = false, defaultFrameMs = 1),
        ShimejiClipId.Divide to PetFrameClip(
            listOf(42, 43, 44, 45, 46),
            loop = false,
            defaultFrameMs = 5,
        ),
    )

    fun clip(id: ShimejiClipId): PetFrameClip = clips.getValue(id)

    fun allClips(): Map<ShimejiClipId, PetFrameClip> = clips
}
