package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeShimejiGrid(
    slots: List<HomeSlotUiModel>,
    onSlotClick: (Int) -> Unit,
    onSlotRemove: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        slots.chunked(COLUMNS).forEachIndexed { rowIdx, rowSlots ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                rowSlots.forEachIndexed { colIdx, model ->
                    val globalIdx = rowIdx * COLUMNS + colIdx
                    HomeShimejiSlot(
                        model = model,
                        onClick = { onSlotClick(globalIdx) },
                        onRemove = { onSlotRemove(globalIdx) },
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(COLUMNS - rowSlots.size) {
                    Row(modifier = Modifier.weight(1f)) {}
                }
            }
        }
    }
}

private const val COLUMNS: Int = 3
