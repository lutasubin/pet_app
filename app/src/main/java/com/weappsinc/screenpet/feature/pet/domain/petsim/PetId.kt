package com.weappsinc.screenpet.feature.pet.domain.petsim

/** Dinh danh the pet trong [PetArenaState]; mo rong nhieu pet bang id khac nhau. */
@JvmInline
value class PetId(val raw: String) {
    companion object {
        val DEFAULT: PetId = PetId("default")
    }
}
