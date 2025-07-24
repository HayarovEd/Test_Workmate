package com.edurda77.test.workmate.ui.list_characters

import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter

sealed interface CharactersScreenAction {
    data object OnNextLoad : CharactersScreenAction
    data object OnRefresh : CharactersScreenAction
    class OnUpdateName (val name: String) : CharactersScreenAction
    class OnUpdateType (val type: String) : CharactersScreenAction
    class OnUpdateSpecies(val species: String) : CharactersScreenAction
    class OnUpdateGender(val gender: Gender) : CharactersScreenAction
    class OnUpdateStatus(val status: StatusCharacter) : CharactersScreenAction
    data object OnResetFilters : CharactersScreenAction
}