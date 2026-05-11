package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.weappsinc.screenpet.feature.pet.presentation.PetSpriteCache

/** Image load tu assets, dung PetSpriteCache de tranh decode lai. */
@Composable
fun AssetImage(
    assetRelativePath: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val context = LocalContext.current
    var bitmap by remember(assetRelativePath) {
        mutableStateOf<ImageBitmap?>(PetSpriteCache.cached(assetRelativePath))
    }
    LaunchedEffect(assetRelativePath) {
        if (bitmap == null) {
            bitmap = PetSpriteCache.load(context, assetRelativePath)
        }
    }
    val bm = bitmap ?: return
    Image(
        painter = BitmapPainter(bm),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}
