package com.edurda77.test.workmate.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharectersResponse(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val resultCharacters: List<ResultCharacter>
)