package com.edurda77.test.workmate.ui.list_characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edurda77.test.workmate.domain.repository.RemoteRepository
import com.edurda77.test.workmate.domain.repository.ServiceRepository
import com.edurda77.test.workmate.domain.utils.Paginator
import com.edurda77.test.workmate.domain.utils.convertToString
import com.edurda77.test.workmate.ui.uikit.asUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private val paginator = Paginator(
        initialKey = 1,
        onLoadUpdated = { isLoading ->
            _state.value.copy(
                isNextLoading = isLoading
            )
                .updateState()
        },
        onRequest = { currentPage ->
            remoteRepository.getCharacters(
                page = currentPage,
                name = state.value.queryName,
                status = state.value.status?.convertToString() ?: "",
                species = state.value.species,
                type = state.value.type,
                gender = state.value.gender?.convertToString() ?: ""
            )
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { throwable ->
            _state.value.copy(
                message = throwable.asUiText()
            )
                .updateState()
        },
        onSuccess = { productsResponse, nextPage ->
            _state.value.copy(
                characters = state.value.characters + productsResponse.characters,
                message = null
            )
                .updateState()

        },
        endReached = { currentPage, response ->
            currentPage >= response.pages
        }
    )
    private val _state = MutableStateFlow(CharactersScreenState())
    val state = _state.asStateFlow()

    init {
        loadNextItems()
    }

    fun onAction(action: CharactersScreenAction) {
        when (action) {
            CharactersScreenAction.OnNextLoad -> loadNextItems()
            CharactersScreenAction.OnRefresh -> {
                viewModelScope.launch {
                    paginator.reset()
                    _state.value.copy(
                        isNextLoading = true,
                        characters = emptyList(),
                        message = null
                    )
                        .updateState()
                    delay(1000)
                    paginator.loadNextItems()
                }
            }

            is CharactersScreenAction.OnUpdateGender -> {
                _state.value.copy(
                    gender = action.gender
                )
                    .updateState()
            }

            is CharactersScreenAction.OnUpdateSpecies -> {
                _state.value.copy(
                    species = action.species
                )
                    .updateState()
            }

            is CharactersScreenAction.OnUpdateStatus -> {
                _state.value.copy(
                    status = action.status
                )
                    .updateState()
            }

            is CharactersScreenAction.OnUpdateType -> {
                _state.value.copy(
                    type = action.type
                )
                    .updateState()
            }

            CharactersScreenAction.OnResetFilters -> {
                _state.value.copy(
                    type = "",
                    species = "",
                    status = null,
                    gender = null
                )
                    .updateState()
            }

            is CharactersScreenAction.OnUpdateName -> {
                _state.value.copy(
                    queryName = action.name
                )
                    .updateState()
            }
        }
    }

    private fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun CharactersScreenState.updateState() {
        _state.update {
            this
        }
    }
}