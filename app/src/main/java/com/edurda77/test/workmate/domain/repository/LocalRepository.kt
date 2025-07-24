package com.edurda77.test.workmate.domain.repository

import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.utils.DataError
import com.edurda77.test.workmate.domain.utils.ResultWork
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertCharacters(characters: List<CharacterDetails>): ResultWork<Unit, DataError.LocalDateBase>
    suspend fun getAllCharacters(): Flow<ResultWork<List<CharacterDetails>, DataError.LocalDateBase>>
    suspend fun getCharacterById(id: Int): ResultWork<CharacterDetails?, DataError.LocalDateBase>
}