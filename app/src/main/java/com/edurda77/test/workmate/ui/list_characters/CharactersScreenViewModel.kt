package com.edurda77.test.workmate.ui.list_characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.repository.LocalRepository
import com.edurda77.test.workmate.domain.repository.RemoteRepository
import com.edurda77.test.workmate.domain.repository.ServiceRepository
import com.edurda77.test.workmate.domain.utils.Paginator
import com.edurda77.test.workmate.domain.utils.ResultWork
import com.edurda77.test.workmate.domain.utils.convertToString
import com.edurda77.test.workmate.ui.uikit.asUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private val localResults = MutableStateFlow<List<CharacterDetails>>(emptyList())

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
            localRepository.insertCharacters(productsResponse.characters)
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
    val state = _state
        .onStart {
            startWork()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CharactersScreenState()
        )


    fun onAction(action: CharactersScreenAction) {
        when (action) {
            CharactersScreenAction.OnNextLoad -> loadNextItems()
            CharactersScreenAction.OnRefresh -> {
                if (state.value.isEnableInternet) {
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
                } else {
                    val filteredData = if (state.value.gender==null&&state.value.status==null) {
                        localResults.value.filter {
                            it.name.contains(state.value.queryName, ignoreCase = true)
                                    && it.species.contains(state.value.species, ignoreCase = true)
                                    && it.type.contains(state.value.type, ignoreCase = true)
                        }
                    } else if (state.value.status==null) {
                        localResults.value.filter {
                            it.name.contains(state.value.queryName, ignoreCase = true)
                                    && it.species.contains(state.value.species, ignoreCase = true)
                                    && it.type.contains(state.value.type, ignoreCase = true)
                                    && it.gender == state.value.gender
                        }
                    } else  if (state.value.gender==null) {
                        localResults.value.filter {
                            it.name.contains(state.value.queryName, ignoreCase = true)
                                    && it.species.contains(state.value.species, ignoreCase = true)
                                    && it.type.contains(state.value.type, ignoreCase = true)
                                    && it.status == state.value.status
                        }
                    } else {
                        localResults.value.filter {
                            it.name.contains(state.value.queryName, ignoreCase = true)
                                    && it.species.contains(state.value.species, ignoreCase = true)
                                    && it.type.contains(state.value.type, ignoreCase = true)
                                    && it.status == state.value.status
                                    && it.gender == state.value.gender
                        }
                    }
                    _state.value.copy(
                        characters = filteredData,
                        isNextLoading = false
                    )
                        .updateState()
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

    private fun startWork() {
        viewModelScope.launch {
            serviceRepository.isConnected().collect {
                if (it) {
                    loadNextItems()
                } else {
                    loadLocalaData()
                }
                _state.value.copy(
                    isEnableInternet = it
                )
                    .updateState()
            }
        }
    }

    private fun loadLocalaData() {
        _state.value.copy(
            isNextLoading = true
        )
            .updateState()
        viewModelScope.launch {
            localRepository.getAllCharacters().collect { collector ->
                when (collector) {
                    is ResultWork.Error -> {
                        _state.value.copy(
                            message = collector.error.asUiText(),
                            isNextLoading = false
                        )
                            .updateState()
                    }

                    is ResultWork.Success -> {
                        localResults.value = collector.data
                        _state.value.copy(
                            characters = collector.data,
                            isNextLoading = false
                        )
                            .updateState()
                    }
                }
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