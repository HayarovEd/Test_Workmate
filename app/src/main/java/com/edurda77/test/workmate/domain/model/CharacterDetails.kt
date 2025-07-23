package com.edurda77.test.workmate.domain.model


data class CharacterDetails(
    val id: Int,
    val name: String,
    val species: String,
    val status: StatusCharacter,
    val imageUrl: String,
    val gender: Gender,
    val location : String,
    val origin: String,
    val episode: List<String>,
)