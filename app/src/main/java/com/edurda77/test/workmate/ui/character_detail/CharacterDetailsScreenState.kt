package com.edurda77.test.workmate.ui.character_detail

import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.ui.uikit.UiText

data class CharacterDetailsScreenState(
    val characterDetails: CharacterDetails? = null,
    val isLoading: Boolean = false,
    val message: UiText? = null,
)