package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.HomeTokens

private val DOWNLOAD_TRACK = Color(0xFFF1D4E5)
private const val BAR_HEIGHT_DP = 10

@Composable
fun ShopDownloadingDialog(progress: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(0.93f),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text(
                text = stringResource(R.string.shop_downloading, progress),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E2142),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            ShopDownloadProgressBar(
                progressFraction = (progress / 100f).coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/** Mot thanh tien trinh (track + fill cung bo goc, khong doi Material hai lop). */
@Composable
private fun ShopDownloadProgressBar(
    progressFraction: Float,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(999.dp)
    BoxWithConstraints(
        modifier = modifier
            .height(BAR_HEIGHT_DP.dp)
            .clip(shape)
            .background(DOWNLOAD_TRACK),
    ) {
        if (progressFraction > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progressFraction)
                    .align(Alignment.CenterStart)
                    .clip(shape)
                    .background(HomeTokens.SwitchCheckedTrack),
            )
        }
    }
}
