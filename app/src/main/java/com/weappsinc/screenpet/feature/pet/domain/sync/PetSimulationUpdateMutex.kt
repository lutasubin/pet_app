package com.weappsinc.screenpet.feature.pet.domain.sync

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex

/** Khoa doc/ghi SSOT pet khi co nhieu nguon tick/su kien (Activity + overlay). */
@Singleton
class PetSimulationUpdateMutex @Inject constructor() {
    val mutex: Mutex = Mutex()
}
