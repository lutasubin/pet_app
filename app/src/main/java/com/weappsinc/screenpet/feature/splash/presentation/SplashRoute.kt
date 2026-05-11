package com.weappsinc.screenpet.feature.splash.presentation

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.core.constants.SplashAssetPaths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SplashRoute(
    viewModel: SplashViewModel,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var background by remember { mutableStateOf<ImageBitmap?>(null) }
    var icon by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        background = decodeAssetImage(context, SplashAssetPaths.BACKGROUND_RELATIVE)
        icon = decodeAssetImage(context, SplashAssetPaths.ICON_RELATIVE)
    }

    LaunchedEffect(state.finished) {
        if (state.finished) onFinished()
    }

    SplashScreen(
        background = background,
        icon = icon,
        progress = state.progress,
        modifier = modifier,
    )
}

private suspend fun decodeAssetImage(context: Context, relativePath: String): ImageBitmap? =
    withContext(Dispatchers.IO) {
        runCatching {
            context.assets.open(relativePath).use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        }.getOrNull()
    }
