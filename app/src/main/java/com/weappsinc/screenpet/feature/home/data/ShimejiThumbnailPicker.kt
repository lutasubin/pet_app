package com.weappsinc.screenpet.feature.home.data

/**
 * Chon chi so frame thumbnail: so nho nhat trong ten file shime (khong phan biet hoa thuong).
 * Pack nhu Zoro khong co shime1.png van hien duoc.
 */
object ShimejiThumbnailPicker {

    private val SHIME_FILE = Regex("(?i)^shime(\\d+)\\.png$")

    fun minFrameIndex(fileNames: List<String>): Int? =
        fileNames.mapNotNull { name ->
            SHIME_FILE.matchEntire(name)?.groupValues?.get(1)?.toIntOrNull()
        }.minOrNull()
}
