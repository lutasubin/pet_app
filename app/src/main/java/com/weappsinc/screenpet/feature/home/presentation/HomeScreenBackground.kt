package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths

/** Nen anh bg-home.png phu full man Home. */
@Composable
fun HomeScreenBackground(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/${HomeAssetPaths.BG_HOME_RELATIVE}")
            .crossfade(false)
            .build(),
        contentDescription = stringResource(R.string.home_background_cd),
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(),
    )
}
