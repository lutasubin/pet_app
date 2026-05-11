package com.weappsinc.screenpet.feature.pet.data

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class PetSimulationRepositoryImpl @Inject constructor() : PetSimulationRepository {

    private val mutex = Mutex()
    private val initialWorld = PetWorldDefaults.initial(PetPlayArea(DEFAULT_W, DEFAULT_H))
    private val _world = MutableStateFlow(initialWorld)

    override val world = _world.asStateFlow()

    override suspend fun replace(newWorld: PetWorldState) {
        mutex.withLock { _world.value = newWorld }
    }

    companion object {
        private const val DEFAULT_W: Int = 480
        private const val DEFAULT_H: Int = 800
    }
}
