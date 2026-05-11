package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths

@Composable
fun HomeBannerCard(modifier: Modifier = Modifier) {
    AssetImage(
        assetRelativePath = HomeAssetPaths.BANNER_RELATIVE,
        contentDescription = stringResource(R.string.home_banner_content_desc),
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop,
    )
}
