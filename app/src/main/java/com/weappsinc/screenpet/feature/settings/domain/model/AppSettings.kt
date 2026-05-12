package com.weappsinc.screenpet.feature.settings.domain.model

/** Cai dat ung dung (SSOT domain). */
data class AppSettings(
    val ghostModeEnabled: Boolean = false,
    /** He so kich thuoc overlay pet (0.75 .. 1.5). */
    val animationSizeScale: Float = DEFAULT_ANIMATION_SIZE_SCALE,
    /** He so tick / toc do (0.5 .. 1.5). */
    val animationSpeedMultiplier: Float = DEFAULT_ANIMATION_SPEED_MULT,
    val sessionDurationMinutes: Int = DEFAULT_SESSION_DURATION_MIN,
    /** "system" | "en" | "vi" */
    val localeTag: String = LOCALE_SYSTEM,
) {
    companion object {
        const val DEFAULT_ANIMATION_SIZE_SCALE: Float = 1f
        const val DEFAULT_ANIMATION_SPEED_MULT: Float = 1f
        const val DEFAULT_SESSION_DURATION_MIN: Int = 5
        const val LOCALE_SYSTEM: String = "system"
        const val MIN_SIZE_SCALE: Float = 0.75f
        const val MAX_SIZE_SCALE: Float = 1.5f
        const val MIN_SPEED_MULT: Float = 0.5f
        const val MAX_SPEED_MULT: Float = 1.5f
    }
}
