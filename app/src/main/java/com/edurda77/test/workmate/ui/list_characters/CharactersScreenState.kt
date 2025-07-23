package com.edurda77.test.workmate.ui.list_characters

import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter
import com.edurda77.test.workmate.ui.uikit.UiText

data class CharactersScreenState(
   // val isLoading: Boolean = false,
    val isNextLoading: Boolean = false,
    val message: UiText? = null,
    val isEnableInternet: Boolean = true,
    val characters: List<CharacterDetails> = emptyList(),
    val queryName: String = "",
    val status: StatusCharacter? = null,
    val species: String = "",
    val type: String = "",
    val gender: Gender? = null,
)