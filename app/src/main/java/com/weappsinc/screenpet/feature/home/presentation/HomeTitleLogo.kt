package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths

/** Logo SVG "Shimeji live" giua header. */
@Composable
fun HomeTitleLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        SvgAssetIcon(
            assetRelativePath = HomeAssetPaths.TITLE_LOGO_SVG_RELATIVE,
            contentDescription = stringResource(R.string.home_title_logo_cd),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(56.dp),
            contentScale = ContentScale.Fit,
        )
    }
}
