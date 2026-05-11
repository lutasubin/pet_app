package com.weappsinc.screenpet.feature.pet.data

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class PetArenaRepositoryImpl @Inject constructor() : PetArenaRepository {

    private val mutex = Mutex()
    private val initial = PetArenaState(
        playArea = PetPlayArea(DEFAULT_W, DEFAULT_H),
        pets = emptyMap(),
    )
    private val _arena = MutableStateFlow(initial)
    override val arena = _arena.asStateFlow()

    override suspend fun replace(newArena: PetArenaState) {
        mutex.withLock { _arena.value = newArena }
    }

    companion object {
        private const val DEFAULT_W: Int = 480
        private const val DEFAULT_H: Int = 800
    }
}
