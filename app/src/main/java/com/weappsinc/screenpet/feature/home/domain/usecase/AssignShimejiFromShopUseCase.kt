package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import javax.inject.Inject

/** Gán Shimeji đã sẵn sàng (mở khóa + tải) vào slot Home khi chọn từ Shop. */
class AssignShimejiFromShopUseCase @Inject constructor(
    private val selectShimejiAtSlot: SelectShimejiAtSlotUseCase,
) {
    suspend operator fun invoke(
        settings: HomeSettings,
        readyIds: Set<String>,
        characterId: String,
    ): Result<Unit> {
        if (characterId !in readyIds) {
            return Result.failure(IllegalStateException("Shimeji chua san sang de them slot"))
        }
        val slot = targetSlotForShopAdd(settings)
        selectShimejiAtSlot(slot, characterId)
        return Result.success(Unit)
    }

    companion object {
        /** Ô trống đầu tiên trong các slot đã mở; nếu đầy thì ghi đè slot 0. */
        fun targetSlotForShopAdd(settings: HomeSettings): Int {
            val n = settings.unlockedSlotCount
            val slots = settings.selectedSlotIds
            return (0 until n).firstOrNull { slots[it] == null } ?: 0
        }
    }
}
