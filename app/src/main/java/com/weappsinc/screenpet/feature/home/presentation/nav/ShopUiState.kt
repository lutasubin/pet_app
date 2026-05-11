package com.weappsinc.screenpet.feature.home.presentation.nav

import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter

data class ShopUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val catalog: List<ShimejiCharacter> = emptyList(),
    val unlockedIds: Set<String> = emptySet(),
    val downloadedIds: Set<String> = emptySet(),
    val downloadingId: String? = null,
    val downloadProgress: Int = 0,
) {
    val visibleCatalog: List<ShimejiCharacter>
        get() = if (query.isBlank()) {
            catalog
        } else {
            val q = query.trim().lowercase()
            catalog.filter { it.displayName.lowercase().contains(q) }
        }
}
