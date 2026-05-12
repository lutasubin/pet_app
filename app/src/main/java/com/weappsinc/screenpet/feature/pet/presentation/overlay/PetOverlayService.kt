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
import com.weappsinc.screenpet.core.util.PetPlayAreaFromWindow
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchArenaInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.TickArenaUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdateArenaPlayAreaUseCase
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Foreground service: ve toan bo pet trong [PetArenaRepository] (swarm-ready). */
@AndroidEntryPoint
class PetOverlayService : Service() {

    @Inject lateinit var repository: PetArenaRepository
    @Inject lateinit var appSettingsRepository: AppSettingsRepository
    @Inject lateinit var tickArenaUseCase: TickArenaUseCase
    @Inject lateinit var updateArenaPlayAreaUseCase: UpdateArenaPlayAreaUseCase
    @Inject lateinit var dispatchArenaInputUseCase: DispatchArenaInputUseCase
    @Inject lateinit var overlaySession: PetOverlaySession

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickJob: Job? = null
    private var observeJob: Job? = null
    private var manager: PetArenaOverlayHostManager? = null

    @Volatile
    private var tickSpeedMultiplier: Float = AppSettings.DEFAULT_ANIMATION_SPEED_MULT

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
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        scope.launch { updateArenaPlayAreaUseCase(PetPlayAreaFromWindow.resolve(wm)) }
        manager = PetArenaOverlayHostManager(
            service = this,
            scope = scope,
            repository = repository,
            updateArenaPlayAreaUseCase = updateArenaPlayAreaUseCase,
            dispatchArenaInputUseCase = dispatchArenaInputUseCase,
        )
        observeArenaAndSettings()
        startTickLoop()
        toast("Pet noi: bat")
    }

    private fun observeArenaAndSettings() {
        observeJob = scope.launch {
            combine(
                repository.arena.map { it.pets.keys.toSet() }.distinctUntilChanged(),
                appSettingsRepository.settings,
            ) { ids, settings -> ids to settings }
                .collectLatest { (ids, settings) ->
                    tickSpeedMultiplier = settings.animationSpeedMultiplier.coerceIn(
                        AppSettings.MIN_SPEED_MULT,
                        AppSettings.MAX_SPEED_MULT,
                    )
                    manager?.sync(ids, settings)
                }
        }
    }

    private fun startTickLoop() {
        tickJob = scope.launch {
            while (isActive) {
                delay(TICK_MS)
                val dt = (TICK_MS * tickSpeedMultiplier).toLong().coerceAtLeast(1L)
                tickArenaUseCase(dt)
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
        observeJob?.cancel()
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
