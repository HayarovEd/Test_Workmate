package com.edurda77.test.workmate.ui.list_characters

sealed interface CharactersScreenAction {
    data object OnNextLoad : CharactersScreenAction
    data object onRefresh : CharactersScreenAction
}