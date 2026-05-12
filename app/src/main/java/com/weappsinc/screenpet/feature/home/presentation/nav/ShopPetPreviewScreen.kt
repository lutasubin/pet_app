package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.core.constants.HomeAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.AssetImage
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayField
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopPetPreviewScreen(
    characterName: String,
    petViewModel: PetViewModel,
    onBack: () -> Unit,
) {
    val world = petViewModel.world.collectAsStateWithLifecycle().value
    DisposableEffect(Unit) {
        petViewModel.startDemoActionPlaylist()
        onDispose { petViewModel.stopAllDemos() }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = characterName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF7C81F5), Color(0xFFB697F7)),
                    ),
                ),
        ) {
            AssetImage(
                assetRelativePath = HomeAssetPaths.BG_HOME_RELATIVE,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            PetPlayField(
                world = world,
                eventSink = petViewModel,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
