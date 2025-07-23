package com.edurda77.test.workmate.domain.repository

import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.CharactersData
import com.edurda77.test.workmate.domain.utils.DataError
import com.edurda77.test.workmate.domain.utils.ResultWork

interface RemoteRepository {
    suspend fun getCharacters(
        page: Int,
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = ""
    ): ResultWork<CharactersData, DataError>

    suspend fun getCharacterById(id: Int): ResultWork<CharacterDetails, DataError>
}