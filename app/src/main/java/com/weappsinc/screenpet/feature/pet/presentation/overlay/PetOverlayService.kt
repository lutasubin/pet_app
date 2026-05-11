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
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchPetInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.TickPetUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdatePlayAreaUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Foreground service: pet TYPE_APPLICATION_OVERLAY, tick khi overlay chiem quyen. */
@AndroidEntryPoint
class PetOverlayService : Service() {

    @Inject lateinit var repository: PetSimulationRepository
    @Inject lateinit var tickPetUseCase: TickPetUseCase
    @Inject lateinit var updatePlayAreaUseCase: UpdatePlayAreaUseCase
    @Inject lateinit var dispatchPetInputUseCase: DispatchPetInputUseCase
    @Inject lateinit var overlaySession: PetOverlaySession
    @Inject lateinit var writeMutex: PetSimulationUpdateMutex

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickJob: Job? = null
    private var windowHost: PetOverlayWindowHost? = null
    private var positionSync: PetOverlayPositionSync? = null

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
        val w = screenWidthPx()
        val h = screenHeightPx()
        Log.i(TAG, "screen=${w}x${h}")
        overlaySession.setActive(true)
        val sink = PetOverlayPlaySink(scope, updatePlayAreaUseCase, dispatchPetInputUseCase)
        sink.onPlayAreaSized(w, h)
        PetOverlaySpawnHelper.spawnAtCenter(scope, repository, writeMutex, w, h)
        val host = PetOverlayWindowHost(this, repository, sink).also { it.attach() }
        windowHost = host
        positionSync = PetOverlayPositionSync(scope, repository, host).also { it.start() }
        tickJob = scope.launch {
            while (isActive) {
                delay(TICK_MS)
                tickPetUseCase(TICK_MS)
            }
        }
        toast("Pet noi: bat")
        Log.i(TAG, "init done")
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
        positionSync?.stop()
        positionSync = null
        windowHost?.detach()
        windowHost = null
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
