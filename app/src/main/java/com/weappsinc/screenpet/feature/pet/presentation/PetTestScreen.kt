package com.weappsinc.screenpet.feature.pet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetTestScreen(
    viewModel: PetViewModel,
    canDrawOverlays: Boolean,
    onOpenOverlaySettings: () -> Unit,
    onStartOverlay: () -> Unit,
    onStopOverlay: () -> Unit,
) {
    val world by viewModel.world.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.pet_test_title)) })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Text(
                text = stringResource(R.string.pet_test_overlay_hint),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(onClick = onStartOverlay) {
                    Text(stringResource(R.string.pet_overlay_start))
                }
                OutlinedButton(onClick = onStopOverlay) {
                    Text(stringResource(R.string.pet_overlay_stop))
                }
            }
            if (!canDrawOverlays) {
                TextButton(onClick = onOpenOverlaySettings) {
                    Text(stringResource(R.string.pet_overlay_grant_overlay))
                }
            }
            val overlayOn by viewModel.overlayActive.collectAsStateWithLifecycle()
            if (overlayOn) {
                Text(
                    text = stringResource(R.string.pet_overlay_running_in_overlay),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                )
            } else {
                PetPlayField(
                    world = world,
                    eventSink = viewModel,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
