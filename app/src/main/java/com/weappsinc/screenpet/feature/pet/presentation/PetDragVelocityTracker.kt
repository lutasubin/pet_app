package com.weappsinc.screenpet.feature.pet.presentation

import android.os.SystemClock
import kotlin.math.max

/**
 * Uoc luong van toc neo pet (px/s) tu mau anchor trong ~150ms cuoi tha tay.
 */
class PetDragVelocityTracker {

    private val timesMs = ArrayDeque<Long>(8)
    private val axPx = ArrayDeque<Float>(8)
    private val ayPx = ArrayDeque<Float>(8)

    fun reset(anchorXPx: Float, anchorYPx: Float) {
        timesMs.clear()
        axPx.clear()
        ayPx.clear()
        val t = SystemClock.uptimeMillis()
        timesMs.addLast(t)
        axPx.addLast(anchorXPx)
        ayPx.addLast(anchorYPx)
    }

    fun addSample(anchorXPx: Float, anchorYPx: Float) {
        val t = SystemClock.uptimeMillis()
        timesMs.addLast(t)
        axPx.addLast(anchorXPx)
        ayPx.addLast(anchorYPx)
        trimOld(t)
    }

    fun velocityPxPerSec(): Pair<Float, Float> {
        if (timesMs.size < 2) return 0f to 0f
        val t0 = timesMs.first()
        val t1 = timesMs.last()
        val dtSec = max((t1 - t0) / 1000f, 0.001f)
        val vx = (axPx.last() - axPx.first()) / dtSec
        val vy = (ayPx.last() - ayPx.first()) / dtSec
        return vx to vy
    }

    private fun trimOld(now: Long) {
        while (timesMs.size > 1 && now - timesMs.first() > WINDOW_MS) {
            timesMs.removeFirst()
            axPx.removeFirst()
            ayPx.removeFirst()
        }
    }

    companion object {
        private const val WINDOW_MS: Long = 150L
    }
}
