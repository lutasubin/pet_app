package com.weappsinc.screenpet.feature.pet.domain.model

/** Loi domain khi advance pet (dung voi Result.failure). */
class PetEngineException(val kind: PetEngineErrorKind) : Exception(kind.name)

enum class PetEngineErrorKind {
    PlayAreaTooSmall,
    InvalidPointer,
}
