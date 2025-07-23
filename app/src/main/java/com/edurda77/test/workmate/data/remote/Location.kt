package com.edurda77.test.workmate.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)