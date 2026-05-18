package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.RateAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.SvgAssetIcon
import com.weappsinc.screenpet.ui.theme.HomeTokens
import com.weappsinc.screenpet.ui.theme.RateSheetTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRateBottomSheet(
    initialStars: Int,
    onDismiss: () -> Unit,
    onSubmit: (Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedStars by remember(initialStars) { mutableIntStateOf(initialStars.coerceIn(0, 5)) }
    val copy = rateSheetCopyFor(selectedStars)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(RateSheetTokens.IllustrationHeight + RateSheetTokens.IllustrationTopPadding)
                    .padding(top = RateSheetTokens.IllustrationTopPadding),
                contentAlignment = Alignment.TopCenter,
            ) {
                SvgAssetIcon(
                    assetRelativePath = RateAssetPaths.illustrationForStars(selectedStars),
                    contentDescription = stringResource(R.string.settings_rate_illustration_cd),
                    modifier = Modifier
                        .offset(y = -RateSheetTokens.TopOverlap)
                        .width(160.dp)
                        .height(RateSheetTokens.IllustrationHeight),
                )
            }
            Spacer(Modifier.height(8.dp))
            copy.titleRes?.let { titleRes ->
                Text(
                    text = stringResource(titleRes),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = RateSheetTokens.BodyText,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
            }
            Text(
                text = stringResource(copy.bodyRes),
                style = MaterialTheme.typography.bodyMedium,
                color = RateSheetTokens.BodyText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.settings_rate_hint),
                style = MaterialTheme.typography.labelLarge,
                color = RateSheetTokens.HintPink,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(12.dp))
            SettingsRateStarsRow(
                selectedStars = selectedStars,
                onSelectStars = { selectedStars = it },
            )
            Spacer(Modifier.height(24.dp))
            RateSubmitButton(
                enabled = selectedStars in 1..5,
                onClick = { onSubmit(selectedStars) },
            )
        }
    }
}

@Composable
private fun RateSubmitButton(enabled: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(Brush.horizontalGradient(HomeTokens.ActivateGradient), alpha = if (enabled) 1f else 0.45f)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.settings_rate_button),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}
