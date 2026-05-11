package com.weappsinc.screenpet.feature.pet.presentation.overlay

import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink

/** Quan ly window overlay kich thuoc bang sprite + cho phep cap nhat vi tri. */
class PetOverlayWindowHost(
    private val service: Service,
    private val repository: PetSimulationRepository,
    private val eventSink: PetPlayEventSink,
) {
    private val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val owner = PetOverlayLifecycleOwner()
    private var composeView: ComposeView? = null
    private var params: WindowManager.LayoutParams? = null

    fun attach() {
        owner.performAttach()
        owner.moveToCreated()
        val themed = ContextThemeWrapper(service, R.style.Theme_App_pet)
        val view = ComposeView(themed).apply {
            setViewTreeLifecycleOwner(owner)
            setViewTreeViewModelStoreOwner(owner)
            setViewTreeSavedStateRegistryOwner(owner)
            setContent { PetOverlayContent(repository, eventSink) }
        }
        val p = buildParams()
        try {
            Log.i(TAG, "addView w=${p.width} h=${p.height} type=${p.type}")
            windowManager.addView(view, p)
            owner.moveToResumed()
            composeView = view
            params = p
            Log.i(TAG, "addView OK")
        } catch (t: Throwable) {
            Log.e(TAG, "addView overlay loi", t)
            owner.moveToDestroyed()
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

    private fun buildParams(): WindowManager.LayoutParams {
        val w = PetSpriteAnchor.SPRITE_WIDTH_PX * PetSpriteAnchor.OVERLAY_DISPLAY_SCALE
        val h = PetSpriteAnchor.SPRITE_HEIGHT_PX * PetSpriteAnchor.OVERLAY_DISPLAY_SCALE
        val p = WindowManager.LayoutParams(
            w,
            h,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            PixelFormat.TRANSLUCENT,
        )
        p.gravity = Gravity.TOP or Gravity.START
        p.x = 0
        p.y = 0
        return p
    }

    companion object {
        private const val TAG = "PetOverlay"
    }
}
