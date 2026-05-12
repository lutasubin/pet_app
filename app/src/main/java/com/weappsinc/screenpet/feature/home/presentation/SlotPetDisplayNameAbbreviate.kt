package com.weappsinc.screenpet.feature.home.presentation

import com.weappsinc.screenpet.ui.theme.HomeTokens

/** Ten hien thi trong o slot: neu qua dai thi viet tat (chu cai dau moi tu) hoac cat + …. */
fun abbreviateSlotPetDisplayName(raw: String): String {
    val trimmed = raw.trim()
    val max = HomeTokens.SlotPetNameMaxDisplayChars
    if (trimmed.length <= max) return trimmed

    val words = trimmed.split(Regex("\\s+")).filter { it.isNotEmpty() }
    if (words.size >= 2) {
        val initials = buildString {
            for (w in words) {
                val c = w.firstOrNull() ?: continue
                append(c.uppercaseChar())
            }
        }
        if (initials.isNotEmpty()) {
            return if (initials.length <= max) initials else initials.take(max)
        }
    }

    val ell = "…"
    val takeCount = (max - ell.length).coerceAtLeast(1)
    return trimmed.take(takeCount) + ell
}
