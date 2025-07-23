package com.edurda77.test.workmate.domain.model


data class CharactersData(
    val characters: List<CharacterDetails>,
    val count: Int,
    val next: String?,
    val pages: Int,
)
