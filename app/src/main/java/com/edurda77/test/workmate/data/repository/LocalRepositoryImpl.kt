package com.edurda77.test.workmate.data.repository

import com.edurda77.test.workmate.data.local.CasheDatabase
import com.edurda77.test.workmate.data.local.CharacterEntity
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.repository.LocalRepository
import com.edurda77.test.workmate.domain.utils.DataError
import com.edurda77.test.workmate.domain.utils.ResultWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LocalRepositoryImpl(
    db: CasheDatabase
) : LocalRepository {
    val dao = db.casheDao

    override suspend fun insertCharacters(
        characters: List<CharacterDetails>
    ): ResultWork<Unit, DataError.LocalDateBase> {
        return withContext(Dispatchers.IO) {
            try {
                dao.insertCharacters(
                    characters.map {
                        CharacterEntity(
                            id = it.id,
                            name = it.name,
                            species = it.species,
                            status = it.status,
                            gender = it.gender,
                            imageUrl = it.imageUrl,
                            location = it.location,
                            origin = it.origin,
                            type = it.type,
                            episode = it.episode
                        )
                    }
                )
                ResultWork.Success(Unit)
            } catch (e: Exception) {
                e.printStackTrace()
                ResultWork.Error(DataError.LocalDateBase.ERROR_WRITE_DATA)
            }
        }
    }

    override suspend fun getAllCharacters(): Flow<ResultWork<List<CharacterDetails>, DataError.LocalDateBase>> {
        return flow {
            try {
                dao.getCharacters().collect { characterList ->
                    val result = characterList.map {
                        it.toCharacterDetails()
                    }
                    emit(ResultWork.Success(result))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultWork.Error(DataError.LocalDateBase.ERROR_READ_DATA))
            }
        }
    }

    override suspend fun getCharacterById(id: Int): ResultWork<CharacterDetails?, DataError.LocalDateBase> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWork.Success(dao.getCharacterById(id)?.toCharacterDetails())
            } catch (e: Exception) {
                e.printStackTrace()
                ResultWork.Error(DataError.LocalDateBase.ERROR_READ_DATA)
            }
        }
    }


    private fun CharacterEntity.toCharacterDetails(): CharacterDetails {
        return CharacterDetails(
            id = id,
            name = name,
            species = species,
            status = status,
            gender = gender,
            imageUrl = imageUrl,
            location = location,
            origin = origin,
            type = type,
            episode = episode
        )
    }
}