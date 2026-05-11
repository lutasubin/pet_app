package com.weappsinc.screenpet.feature.home.domain.model

/** Trang thai cau hinh tu Home (luu DataStore). 6 slot Shimeji. */
data class HomeSettings(
    val activateEnabled: Boolean = false,
    val swarmEnabled: Boolean = false,
    val selectedSlotIds: List<String?> = List(SLOT_COUNT) { null },
    val unlockedSlotCount: Int = 1,
) {
    init {
        require(selectedSlotIds.size == SLOT_COUNT) {
            "selectedSlotIds phai co dung $SLOT_COUNT phan tu"
        }
        require(unlockedSlotCount in 1..SLOT_COUNT) {
            "unlockedSlotCount phai trong 1..$SLOT_COUNT"
        }
    }

    fun withSlot(slot: Int, characterId: String?): HomeSettings {
        require(slot in 0 until SLOT_COUNT) { "slot ngoai pham vi" }
        val next = selectedSlotIds.toMutableList()
        next[slot] = characterId
        return copy(selectedSlotIds = next)
    }

    companion object {
        const val SLOT_COUNT: Int = 6
    }
}
