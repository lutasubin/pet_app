package com.weappsinc.screenpet.feature.onboarding.presentation.page

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.weappsinc.screenpet.feature.home.presentation.AssetImage
import com.weappsinc.screenpet.feature.onboarding.presentation.idleBob

/**
 * Dat 1 pet PNG vao Box theo bias (-1..1) + idle bob.
 * Tach rieng de moi page chi viet 1 dong cho moi pet, file ngan.
 */
@Composable
fun BoxScope.PetLayer(
    assetPath: String,
    biasH: Float,
    biasV: Float,
    size: Dp,
    contentDescription: String?,
    phaseOffsetMs: Int = 0,
) {
    AssetImage(
        assetRelativePath = assetPath,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .align(BiasAlignment(biasH, biasV))
            .size(size)
            .idleBob(phaseOffsetMs = phaseOffsetMs),
    )
}
