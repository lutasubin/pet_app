package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

/** Hien thi icon SVG tu android_asset. */
@Composable
fun SvgAssetIcon(
    assetRelativePath: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val req = ImageRequest.Builder(context)
        .data("file:///android_asset/$assetRelativePath")
        .decoderFactory(SvgDecoder.Factory())
        .crossfade(false)
        .build()
    AsyncImage(
        model = req,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
