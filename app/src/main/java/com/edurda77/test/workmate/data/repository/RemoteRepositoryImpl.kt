package com.edurda77.test.workmate.data.repository

import com.edurda77.test.workmate.data.handler.handleRemoteResponse
import com.edurda77.test.workmate.data.remote.CharectersResponse
import com.edurda77.test.workmate.data.remote.ResultCharacter
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.CharactersData
import com.edurda77.test.workmate.domain.repository.RemoteRepository
import com.edurda77.test.workmate.domain.utils.DataError
import com.edurda77.test.workmate.domain.utils.ResultWork
import com.edurda77.test.workmate.domain.utils.convertToGender
import com.edurda77.test.workmate.domain.utils.convertToStatusCharacter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl(
    private val client: HttpClient,
) : RemoteRepository {

    override suspend fun getCharacters(
        page: Int,
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String,
    ): ResultWork<CharactersData, DataError> {
        return withContext(Dispatchers.IO) {
            handleRemoteResponse {
                val response = client.get("character/") {
                    contentType(ContentType.Application.Json)
                    parameter("page", page)
                    parameter("name", name)
                    parameter("status", status)
                    parameter("species", species)
                    parameter("type", type)
                    parameter("gender", gender)
                }.call
                    .body<CharectersResponse>()
                CharactersData(
                    count = response.info.count,
                    next = response.info.next,
                    pages = response.info.pages,
                    characters = response.resultCharacters.map {
                        it.toCharacterDetails()
                    }
                )
            }
        }
    }

    override suspend fun getCharacterById(
        id: Int,
    ): ResultWork<CharacterDetails, DataError> {
        return withContext(Dispatchers.IO) {
            handleRemoteResponse {
                val response = client.get("character/$id") {
                    contentType(ContentType.Application.Json)
                }.call
                    .body<ResultCharacter>()
                response.toCharacterDetails()
            }
        }
    }

    private fun ResultCharacter.toCharacterDetails(): CharacterDetails {
        return CharacterDetails(
            id = id,
            name = name,
            species = species,
            imageUrl = image,
            status = status.convertToStatusCharacter(),
            gender = gender.convertToGender(),
            location = location.name,
            origin = origin.name,
            type = type,
            episode = episode.map { it.substringAfterLast("/") }
        )
    }
}
