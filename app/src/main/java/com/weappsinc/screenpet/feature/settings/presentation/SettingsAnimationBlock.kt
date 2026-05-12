package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.SettingsAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.SvgAssetIcon
import com.weappsinc.screenpet.feature.home.presentation.homeToggleSwitchColors
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun SettingsAnimationBlock(
    settings: AppSettings,
    onGhostChanged: (Boolean) -> Unit,
    onSizeChangeFinished: (Float) -> Unit,
    onSpeedChangeFinished: (Float) -> Unit,
) {
    SettingsWhiteCard(title = stringResource(R.string.settings_section_animation)) {
        GhostRow(settings.ghostModeEnabled, onGhostChanged)
        Spacer(Modifier.height(16.dp))
        SliderRow(
            path = SettingsAssetPaths.SIZE_RELATIVE,
            title = stringResource(R.string.settings_size_title),
            value = settings.animationSizeScale,
            range = AppSettings.MIN_SIZE_SCALE..AppSettings.MAX_SIZE_SCALE,
            steps = 14,
            onChangeFinished = onSizeChangeFinished,
        )
        Spacer(Modifier.height(20.dp))
        SliderRow(
            path = SettingsAssetPaths.SPEED_RELATIVE,
            title = stringResource(R.string.settings_speed_title),
            value = settings.animationSpeedMultiplier,
            range = AppSettings.MIN_SPEED_MULT..AppSettings.MAX_SPEED_MULT,
            steps = 9,
            onChangeFinished = onSpeedChangeFinished,
        )
    }
}

@Composable
private fun SettingsWhiteCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = HomeTokens.SwarmTitle,
        )
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun GhostRow(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SvgAssetIcon(
            assetRelativePath = SettingsAssetPaths.GHOST_RELATIVE,
            contentDescription = stringResource(R.string.settings_icon_cd, stringResource(R.string.settings_ghost_title)),
            modifier = Modifier.size(40.dp),
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.settings_ghost_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = HomeTokens.SwarmTitle,
            )
            Text(
                text = stringResource(R.string.settings_ghost_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = HomeTokens.SlotLockedLabel,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = homeToggleSwitchColors(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderRow(
    path: String,
    title: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    steps: Int,
    onChangeFinished: (Float) -> Unit,
) {
    var local by remember { mutableFloatStateOf(value) }
    LaunchedEffect(value) { local = value }
    val interactionSource = remember { MutableInteractionSource() }
    val sliderColors = SliderDefaults.colors(
        thumbColor = HomeTokens.SwitchCheckedTrack,
        activeTrackColor = HomeTokens.SwitchCheckedTrack,
        inactiveTrackColor = HomeTokens.SettingsSliderInactiveTrack,
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SvgAssetIcon(
                assetRelativePath = path,
                contentDescription = stringResource(R.string.settings_icon_cd, title),
                modifier = Modifier.size(40.dp),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = HomeTokens.SwarmTitle,
                modifier = Modifier.weight(1f),
            )
        }
        Slider(
            value = local,
            onValueChange = { local = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            enabled = true,
            valueRange = range,
            steps = steps,
            onValueChangeFinished = { onChangeFinished(local) },
            colors = sliderColors,
            interactionSource = interactionSource,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    colors = sliderColors,
                    enabled = true,
                    thumbSize = DpSize(
                        HomeTokens.SettingsSliderThumbSizeDp,
                        HomeTokens.SettingsSliderThumbSizeDp,
                    ),
                )
            },
            track = { sliderState ->
                SettingsPinkThinTrack(
                    sliderState = sliderState,
                    activeColor = HomeTokens.SwitchCheckedTrack,
                    inactiveColor = HomeTokens.SettingsSliderInactiveTrack,
                )
            },
        )
    }
}

