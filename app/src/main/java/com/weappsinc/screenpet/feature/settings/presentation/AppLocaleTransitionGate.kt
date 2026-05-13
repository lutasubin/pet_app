package com.weappsinc.screenpet.feature.settings.presentation

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * State song xuyen MainActivity.recreate() — luu thoi diem ket thuc loading.
 *
 * Su dung Compose state ([mutableLongStateOf]) thay vi poll de overlay phan ung
 * NGAY trong frame ke tiep sau khi [begin]. Tranh do tre 50ms cua poll → bot nhay.
 */
object AppLocaleTransitionGate {

    /** Snapshot state: thoi diem (elapsedRealtime) overlay phai bien mat. */
    var activeUntilElapsedMs by mutableLongStateOf(0L)
        private set

    /** Bat overlay loading trong [durationMs] ms (tinh tu now). */
    fun begin(durationMs: Long = DEFAULT_TRANSITION_MS) {
        activeUntilElapsedMs = SystemClock.elapsedRealtime() + durationMs
    }

    fun isActive(): Boolean = SystemClock.elapsedRealtime() < activeUntilElapsedMs

    fun clear() {
        activeUntilElapsedMs = 0L
    }

    /** Manifest co configChanges=locale → khong recreate; chi can du de show feedback. */
    const val DEFAULT_TRANSITION_MS: Long = 450L
}

/**
 * Compose state phan anh [AppLocaleTransitionGate.isActive].
 *
 * Lan thuc thi dau: doc [AppLocaleTransitionGate.activeUntilElapsedMs] -> Compose se
 * tu dong recompose khi gia tri doi (gate.begin()). Sau do dat LaunchedEffect cho het han.
 */
@Composable
fun rememberAppLocaleTransitionActive(): State<Boolean> {
    val until = AppLocaleTransitionGate.activeUntilElapsedMs
    val state = remember { mutableStateOf(SystemClock.elapsedRealtime() < until) }
    LaunchedEffect(until) {
        val now = SystemClock.elapsedRealtime()
        if (now >= until) {
            state.value = false
            return@LaunchedEffect
        }
        state.value = true
        delay(until - now)
        state.value = false
    }
    return state
}
