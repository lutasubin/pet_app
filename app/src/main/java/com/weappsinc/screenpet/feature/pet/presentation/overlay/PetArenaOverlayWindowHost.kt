package com.weappsinc.screenpet.feature.pet.presentation.overlay

import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import kotlin.math.roundToInt

/** Window TYPE_APPLICATION_OVERLAY cho mot pet trong arena. */
class PetArenaOverlayWindowHost(
    private val service: Service,
    private val repository: PetArenaRepository,
    private val petId: PetId,
    private val eventSink: PetPlayEventSink,
) {
    private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val owner = PetOverlayLifecycleOwner()
    private var composeView: ComposeView? = null
    private var params: WindowManager.LayoutParams? = null

    private val overlayScaleState = mutableFloatStateOf(AppSettings.DEFAULT_ANIMATION_SIZE_SCALE)
    private val ghostModeState = mutableStateOf(false)

    val displayScalePx: Float get() = overlayScaleState.floatValue

    fun attach(initial: AppSettings) {
        applyModel(initial, updateLayoutOnly = false)
        owner.performAttach()
        owner.moveToCreated()
        val themed = ContextThemeWrapper(service, R.style.Theme_App_pet)
        val view = ComposeView(themed).apply {
            setViewTreeLifecycleOwner(owner)
            setViewTreeViewModelStoreOwner(owner)
            setViewTreeSavedStateRegistryOwner(owner)
            setContent {
                PetArenaOverlaySpriteHost(
                    repository = repository,
                    petId = petId,
                    eventSink = eventSink,
                    displayScale = overlayScaleState.floatValue,
                )
            }
        }
        val p = buildLayoutParams()
        try {
            Log.i(TAG, "addView petId=${petId.raw}")
            windowManager.addView(view, p)
            owner.moveToResumed()
            composeView = view
            params = p
        } catch (t: Throwable) {
            Log.e(TAG, "addView loi", t)
            owner.moveToDestroyed()
        }
    }

    fun applyAppSettings(settings: AppSettings) {
        applyModel(settings, updateLayoutOnly = true)
    }

    private fun applyModel(settings: AppSettings, updateLayoutOnly: Boolean) {
        val scale = settings.animationSizeScale.coerceIn(AppSettings.MIN_SIZE_SCALE, AppSettings.MAX_SIZE_SCALE)
        overlayScaleState.floatValue = scale
        ghostModeState.value = settings.ghostModeEnabled
        if (updateLayoutOnly) {
            val view = composeView ?: return
            val p = params ?: return
            p.width = (PetSpriteAnchor.SPRITE_WIDTH_PX * scale).roundToInt().coerceAtLeast(32)
            p.height = (PetSpriteAnchor.SPRITE_HEIGHT_PX * scale).roundToInt().coerceAtLeast(32)
            p.flags = overlayFlags(ghostModeState.value)
            runCatching { windowManager.updateViewLayout(view, p) }
        }
    }

    fun update(xPx: Int, yPx: Int) {
        val view = composeView ?: return
        val p = params ?: return
        if (p.x == xPx && p.y == yPx) return
        p.x = xPx
        p.y = yPx
        runCatching { windowManager.updateViewLayout(view, p) }
    }

    fun detach() {
        val view = composeView ?: return
        runCatching { windowManager.removeView(view) }
        view.disposeComposition()
        composeView = null
        params = null
        owner.moveToDestroyed()
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        val scale = overlayScaleState.floatValue
        val w = (PetSpriteAnchor.SPRITE_WIDTH_PX * scale).roundToInt().coerceAtLeast(32)
        val h = (PetSpriteAnchor.SPRITE_HEIGHT_PX * scale).roundToInt().coerceAtLeast(32)
        val p = WindowManager.LayoutParams(
            w,
            h,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            overlayFlags(ghostModeState.value),
            PixelFormat.TRANSLUCENT,
        )
        p.gravity = Gravity.TOP or Gravity.START
        return p
    }

    private fun overlayFlags(ghost: Boolean): Int {
        val base = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        return if (ghost) {
            base or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        } else {
            base or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        }
    }

    companion object {
        private const val TAG = "PetOverlay"
    }
}
