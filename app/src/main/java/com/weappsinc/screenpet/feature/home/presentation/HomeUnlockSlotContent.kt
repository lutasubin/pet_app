package com.weappsinc.screenpet.feature.home.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths
import com.weappsinc.screenpet.ui.theme.HomeTokens

/** Noi dung o slot khoa / mo khoa: nen card + vong trang icon, chu dam (mockup Unlock). */
@Composable
internal fun HomeUnlockSlotContent(
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .shadow(
                    elevation = HomeTokens.SlotUnlockIconRingElevationDp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = HomeTokens.SlotUnlockIconRingShadowAmbient,
                    spotColor = HomeTokens.SlotUnlockIconRingShadowSpot,
                )
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            SvgAssetIcon(
                assetRelativePath = HomeAssetPaths.UNLOCK_ICON_RELATIVE,
                contentDescription = stringResource(R.string.home_lock_cd),
                modifier = Modifier.size(28.dp),
                tint = HomeTokens.LockIcon,
            )
        }
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomeTokens.SlotUnlockCardTitle,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.padding(top = 10.dp),
        )
    }
}
