package com.weappsinc.screenpet.core.constants

/** SVG minh hoa bottom sheet danh gia (0..5 sao). */
object RateAssetPaths {
    fun illustrationForStars(stars: Int): String = when (stars.coerceIn(0, 5)) {
        0 -> "svg/rate0.svg"
        1 -> "svg/rate1.svg"
        2 -> "svg/rate2.svg"
        3 -> "svg/rate3.svg"
        4 -> "svg/rate4.svg"
        else -> "svg/rate5.svg"
    }
}
