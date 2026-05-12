package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.presentation.AssetImage
import com.weappsinc.screenpet.feature.home.presentation.SvgAssetIcon

@Composable
fun ShopPetCard(
    character: ShimejiCharacter,
    unlocked: Boolean,
    downloaded: Boolean,
    onUnlock: () -> Unit,
    onDownload: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AssetImage(
                        assetRelativePath = character.thumbnailAssetPath,
                        contentDescription = character.displayName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                    )
                    if (!unlocked) {
                        SvgAssetIcon(
                            assetRelativePath = HomeAssetPaths.UNLOCK_ICON_RELATIVE,
                            contentDescription = stringResource(R.string.home_lock_cd),
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopStart),
                        )
                    }
                }
                Text(
                    text = character.displayName,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1E2142),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        ShopPetActionButton(
            unlocked = unlocked,
            downloaded = downloaded,
            onUnlock = onUnlock,
            onDownload = onDownload,
            onSelect = onSelect,
        )
    }
}
