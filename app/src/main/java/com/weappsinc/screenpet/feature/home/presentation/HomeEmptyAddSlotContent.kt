package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.HomeTokens

/** O trong "them Shimeji": nen trang + vien dut (modifier ngoai), them sparkle goc, vong hong + dau + nhu cu. */
@Composable
internal fun HomeEmptyAddSlotContent() {
    val sparkle = HomeTokens.SlotEmptyDashBorder
    Box(Modifier.fillMaxSize()) {
        CornerSparklePair(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 10.dp),
            sparkleColor = sparkle,
            corner = SparkleCorner.TopEnd,
        )
        CornerSparklePair(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 12.dp, start = 10.dp),
            sparkleColor = sparkle,
            corner = SparkleCorner.BottomStart,
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(52.dp)
                .clip(CircleShape)
                .background(HomeTokens.SlotEmptyCircleFill),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.home_add_shimeji_cd),
                tint = HomeTokens.NavActive,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}

private enum class SparkleCorner { TopEnd, BottomStart }

@Composable
private fun CornerSparklePair(
    modifier: Modifier,
    sparkleColor: Color,
    corner: SparkleCorner,
) {
    Box(modifier) {
        when (corner) {
            SparkleCorner.TopEnd -> {
                Text(
                    text = "✦",
                    color = sparkleColor,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.TopEnd),
                )
                Text(
                    text = "✦",
                    color = sparkleColor,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 9.dp),
                )
            }
            SparkleCorner.BottomStart -> {
                Text(
                    text = "✦",
                    color = sparkleColor,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.BottomStart),
                )
                Text(
                    text = "✦",
                    color = sparkleColor,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = 8.dp, y = (-9).dp),
                )
            }
        }
    }
}
