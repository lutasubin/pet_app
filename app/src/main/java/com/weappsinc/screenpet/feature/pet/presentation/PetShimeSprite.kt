package com.weappsinc.screenpet.feature.pet.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor

@Composable
fun PetShimeSprite(
    assetRelativePath: String,
    modifier: Modifier = Modifier,
    flipHorizontal: Boolean = false,
    sizePxOverride: Int? = null,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val w = sizePxOverride ?: PetSpriteAnchor.SPRITE_WIDTH_PX
    val h = sizePxOverride ?: PetSpriteAnchor.SPRITE_HEIGHT_PX
    val widthDp = with(density) { w.toDp() }
    val heightDp = with(density) { h.toDp() }
    var current by remember { mutableStateOf<ImageBitmap?>(PetSpriteCache.cached(assetRelativePath)) }
    LaunchedEffect(assetRelativePath) {
        val cached = PetSpriteCache.cached(assetRelativePath)
        if (cached != null) {
            current = cached
        } else {
            val loaded = PetSpriteCache.load(context, assetRelativePath)
            if (loaded != null) current = loaded
        }
    }
    val bm = current ?: return
    Image(
        bitmap = bm,
        contentDescription = stringResource(R.string.pet_sprite_content_desc),
        modifier = modifier
            .size(widthDp, heightDp)
            .graphicsLayer { scaleX = if (flipHorizontal) -1f else 1f },
    )
}
