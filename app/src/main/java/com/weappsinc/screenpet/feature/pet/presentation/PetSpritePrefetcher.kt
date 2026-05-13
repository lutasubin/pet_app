package com.weappsinc.screenpet.feature.pet.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.weappsinc.screenpet.core.constants.PetAssetPaths
import com.weappsinc.screenpet.core.constants.ShimejiFrameIndices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Tai san truoc tat ca frame shime de tranh nhay khi doi frame. */
@Composable
fun PetSpritePrefetcher(shimeAssetFolder: String = PetAssetPaths.SHIME_IMAGE_FOLDER) {
    val context = LocalContext.current
    LaunchedEffect(shimeAssetFolder) {
        withContext(Dispatchers.IO) {
            for (i in ShimejiFrameIndices.MIN..ShimejiFrameIndices.MAX) {
                PetSpriteCache.load(context, PetAssetPaths.shimeRelativePath(shimeAssetFolder, i))
            }
        }
    }
}
