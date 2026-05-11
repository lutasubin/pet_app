package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiFrameCatalog
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot

/**
 * Tang frame trong clip theo thoi gian.
 * `PetFrameClip.defaultFrameMs` la so "tick Shimeji-EE" (giong actions.xml goc),
 * khong phai milliseconds. Nhan voi [SHIMEJI_TICK_MS] de ra ms thuc te.
 */
object PetClipPlayback {
    const val SHIMEJI_TICK_MS: Int = 20

    fun advance(snapshot: PetSnapshot, dtMs: Long): PetSnapshot {
        val clip = ShimejiFrameCatalog.clip(snapshot.clipId)
        val frames = clip.frameIndices
        if (frames.isEmpty()) return snapshot
        val frameMs = (clip.defaultFrameMs * SHIMEJI_TICK_MS).coerceAtLeast(1)
        var ms = snapshot.msAccumulatedInFrame + dtMs
        var idx = snapshot.frameIndex.coerceIn(0, frames.lastIndex)
        while (ms >= frameMs) {
            ms -= frameMs
            idx++
            if (idx >= frames.size) {
                if (clip.loop) {
                    idx = 0
                } else {
                    idx = frames.lastIndex
                    break
                }
            }
        }
        return snapshot.copy(frameIndex = idx, msAccumulatedInFrame = ms)
    }
}
