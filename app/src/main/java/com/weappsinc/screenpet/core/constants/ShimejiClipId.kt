package com.weappsinc.screenpet.core.constants

/**
 * Dinh danh clip theo hanh vi gan voi Shimeji-EE (conf/actions.xml).
 * Mot so clip ghep lai cho preview (vi du keo chuot) khac dieu kien runtime goc.
 */
enum class ShimejiClipId {
    Stand,
    /** Chu ky di chuyen chuan EE: 1-2-1-3 */
    Walk,
    /** Bien the ngan: 1-2-3 (khong lap lai frame 1 giua chung) */
    WalkSimple,
    Run,
    Dash,
    Sit,
    SitLookUp,
    SitSpinHead,
    SitLegsUp,
    SitLegsDown,
    SitDangleLegs,
    Sprawl,
    Creep,
    GrabCeiling,
    ClimbCeiling,
    GrabWall,
    ClimbWall,
    Falling,
    Bouncing,
    Tripping,
    Jumping,
    /** EE chon frame theo FootX vs chuot; day la thu tu preview day du */
    PinchDragPreview,
    Resisting,
    FallWithIe,
    WalkWithIe,
    RunWithIe,
    ThrowIe,
    PullUpSpawn,
    PullUpFlung,
    Divide,
}
