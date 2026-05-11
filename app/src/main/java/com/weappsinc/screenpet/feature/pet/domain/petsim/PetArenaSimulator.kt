package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.engine.PetEngine
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea

/**
 * Dieu phoi nhieu pet tren cung [PetPlayArea]: moi buoc goi [PetEngine] tung the.
 * Mo rong: them [PetArenaState.pets], UI gui [PetArenaInput] kem [PetId].
 */
object PetArenaSimulator {

    fun advance(arena: PetArenaState, input: PetArenaInput): Result<PetArenaState> = runCatching {
        when (input) {
            is PetArenaInput.TickAll -> arena.copy(
                pets = arena.pets.mapValues { (_, e) ->
                    advanceEntity(arena.playArea, e, PetInput.Tick(input.dtMs))
                },
            )
            is PetArenaInput.Layout -> {
                val na = input.playArea
                arena.copy(
                    playArea = na,
                    pets = arena.pets.mapValues { (_, e) ->
                        advanceEntity(na, e, PetInput.Layout(na))
                    },
                )
            }
            is PetArenaInput.PointerDown -> arena.copy(
                pets = mapOnePet(arena, input.petId) { e ->
                    advanceEntity(arena.playArea, e, PetInput.PointerDown(input.xPx, input.yPx))
                },
            )
            is PetArenaInput.PointerMove -> arena.copy(
                pets = mapOnePet(arena, input.petId) { e ->
                    advanceEntity(arena.playArea, e, PetInput.PointerMove(input.xPx, input.yPx))
                },
            )
            is PetArenaInput.PointerUp -> arena.copy(
                pets = mapOnePet(arena, input.petId) { e ->
                    advanceEntity(
                        arena.playArea,
                        e,
                        PetInput.PointerUp(
                            input.releaseVelocityXPxPerSec,
                            input.releaseVelocityYPxPerSec,
                        ),
                    )
                },
            )
        }
    }

    private fun advanceEntity(playArea: PetPlayArea, entity: PetEntityState, petInput: PetInput): PetEntityState {
        val w = entity.toWorld(playArea)
        val nw = PetEngine.advance(w, petInput).getOrThrow()
        return PetEntityState.fromWorld(nw)
    }

    private fun mapOnePet(
        arena: PetArenaState,
        petId: PetId,
        block: (PetEntityState) -> PetEntityState,
    ): Map<PetId, PetEntityState> {
        if (!arena.pets.containsKey(petId)) return arena.pets
        return arena.pets.mapValues { (id, e) -> if (id == petId) block(e) else e }
    }
}
