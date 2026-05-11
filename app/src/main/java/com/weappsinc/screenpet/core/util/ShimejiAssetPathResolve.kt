package com.weappsinc.screenpet.core.util

import android.content.Context

/**
 * Pack asset khac ten file chuan:
 * - "Shime1.png" / "SHIME1.png" (Boyfriend…)
 * - "shime1.PNG" (Fundy, BadBoyHalo trong data2)
 * Chon duong dan ton tai trong AssetManager.
 */
object ShimejiAssetPathResolve {

    private val LOWER_SHIME_FILE = Regex("^(.*/)shime(\\d+\\.png)$")

    fun existingAssetPath(context: Context, relativePath: String): String =
        expandAssetPathCandidates(relativePath).firstOrNull { assetExists(context, it) } ?: relativePath

    /** Thu tu: path goc, doi .png/.PNG, roi Shime/SHIME + doi duoi. */
    internal fun expandAssetPathCandidates(path: String): List<String> {
        val ordered = LinkedHashSet<String>()
        fun addBothExt(base: String) {
            if (!ordered.add(base)) return
            when {
                base.endsWith(".png") -> ordered.add(base.dropLast(4) + ".PNG")
                base.endsWith(".PNG") -> ordered.add(base.dropLast(4) + ".png")
            }
        }
        addBothExt(path)
        val norm = path.replace(Regex("(?i)\\.png$"), ".png")
        uppercaseShimeVariant(norm)?.let { addBothExt(it) }
        allCapsShimeVariant(norm)?.let { addBothExt(it) }
        return ordered.toList()
    }

    /** data1/X/shime1.png -> data1/X/Shime1.png */
    internal fun uppercaseShimeVariant(path: String): String? {
        val n = path.replace(Regex("(?i)\\.png$"), ".png")
        val m = LOWER_SHIME_FILE.matchEntire(n) ?: return null
        return "${m.groupValues[1]}Shime${m.groupValues[2]}"
    }

    /** data1/X/shime1.png -> data1/X/SHIME1.png */
    internal fun allCapsShimeVariant(path: String): String? {
        val n = path.replace(Regex("(?i)\\.png$"), ".png")
        val m = LOWER_SHIME_FILE.matchEntire(n) ?: return null
        return "${m.groupValues[1]}SHIME${m.groupValues[2]}"
    }

    private fun assetExists(context: Context, relativePath: String): Boolean =
        runCatching {
            context.assets.open(relativePath).use { }
            true
        }.getOrDefault(false)
}
