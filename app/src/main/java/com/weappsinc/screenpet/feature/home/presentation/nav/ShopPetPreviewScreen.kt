package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayField
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel

/** Chỉ hình nền preview + pet; phím Back hệ thống gọi onBack. */
@Composable
fun ShopPetPreviewScreen(
    petViewModel: PetViewModel,
    onBack: () -> Unit,
) {
    val world by petViewModel.world.collectAsStateWithLifecycle()
    BackHandler(onBack = onBack)
    DisposableEffect(Unit) {
        petViewModel.startShopPreviewLiveMode()
        onDispose { petViewModel.stopAllDemos() }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        ShopPetPreviewBackdrop(Modifier.fillMaxSize())
        PetPlayField(
            world = world,
            eventSink = petViewModel,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
