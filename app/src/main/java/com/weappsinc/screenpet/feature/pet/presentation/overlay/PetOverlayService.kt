package com.weappsinc.screenpet.feature.pet.presentation.overlay

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.weappsinc.screenpet.core.constants.PetOverlayContract
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchArenaInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.TickArenaUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdateArenaPlayAreaUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Foreground service: ve toan bo pet trong [PetArenaRepository] (swarm-ready). */
@AndroidEntryPoint
class PetOverlayService : Service() {

    @Inject lateinit var repository: PetArenaRepository
    @Inject lateinit var tickArenaUseCase: TickArenaUseCase
    @Inject lateinit var updateArenaPlayAreaUseCase: UpdateArenaPlayAreaUseCase
    @Inject lateinit var dispatchArenaInputUseCase: DispatchArenaInputUseCase
    @Inject lateinit var overlaySession: PetOverlaySession

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickJob: Job? = null
    private var manager: PetArenaOverlayHostManager? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        PetOverlayForeground.start(this)
        if (!Settings.canDrawOverlays(this)) {
            Log.w(TAG, "canDrawOverlays = false -> stop")
            toast("Pet noi: chua co quyen overlay")
            stopSelf()
            return
        }
        overlaySession.setActive(true)
        scope.launch { updateArenaPlayAreaUseCase(PetPlayArea(screenWidthPx(), screenHeightPx())) }
        manager = PetArenaOverlayHostManager(
            service = this,
            scope = scope,
            repository = repository,
            updateArenaPlayAreaUseCase = updateArenaPlayAreaUseCase,
            dispatchArenaInputUseCase = dispatchArenaInputUseCase,
        )
        observeArenaPets()
        startTickLoop()
        toast("Pet noi: bat")
    }

    private fun observeArenaPets() {
        scope.launch {
            repository.arena
                .map { it.pets.keys.toSet() }
                .distinctUntilChanged()
                .collectLatest { manager?.sync(it) }
        }
    }

    private fun startTickLoop() {
        tickJob = scope.launch {
            while (isActive) {
                delay(TICK_MS)
                tickArenaUseCase(TICK_MS)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == PetOverlayContract.ACTION_STOP) {
            stopSelf()
            return START_NOT_STICKY
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        tickJob?.cancel()
        manager?.detachAll()
        manager = null
        scope.cancel()
        overlaySession.setActive(false)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun screenWidthPx(): Int {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        return wm.currentWindowMetrics.bounds.width()
    }

    private fun screenHeightPx(): Int {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        return wm.currentWindowMetrics.bounds.height()
    }

    companion object {
        private const val TAG = "PetOverlay"
        private const val TICK_MS = 16L

        fun start(context: Context) {
            ContextCompat.startForegroundService(
                context,
                Intent(context, PetOverlayService::class.java),
            )
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, PetOverlayService::class.java))
        }
    }
}
