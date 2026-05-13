package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayField
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel

/** Nen full man + app bar “xem trước”; pet chi trong vung duoi bar (bot nav + dem). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopPetPreviewScreen(
    character: ShimejiCharacter,
    petViewModel: PetViewModel,
    onBack: () -> Unit,
) {
    val world by petViewModel.world.collectAsStateWithLifecycle()
    val folder = character.spriteAssetFolder()
    BackHandler(onBack = onBack)
    DisposableEffect(Unit) {
        onDispose { petViewModel.stopAllDemos() }
    }
    LaunchedEffect(character.id) {
        petViewModel.startShopPreviewLiveMode()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        ShopPetPreviewBackdrop(Modifier.fillMaxSize())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.shop_pet_preview_title),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.shop_pet_preview_back_cd),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                        scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    ),
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .navigationBarsPadding()
                    .padding(bottom = 88.dp),
            ) {
                PetPlayField(
                    world = world,
                    eventSink = petViewModel,
                    shimeAssetFolder = folder,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
