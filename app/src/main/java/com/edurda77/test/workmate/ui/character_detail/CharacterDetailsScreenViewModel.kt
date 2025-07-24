package com.edurda77.test.workmate.ui.character_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.edurda77.test.workmate.domain.repository.LocalRepository
import com.edurda77.test.workmate.domain.repository.RemoteRepository
import com.edurda77.test.workmate.domain.repository.ServiceRepository
import com.edurda77.test.workmate.domain.utils.ResultWork
import com.edurda77.test.workmate.ui.navigation.NavigationRoute
import com.edurda77.test.workmate.ui.uikit.asUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val serviceRepository: ServiceRepository,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<NavigationRoute.CharecterDetailsRoute>().id

    private val _state = MutableStateFlow(CharacterDetailsScreenState())
    val state = _state
        .onStart {
            startWork()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CharacterDetailsScreenState()
        )

    private fun startWork() {
        viewModelScope.launch {
            _state.value.copy(
                isLoading = true,
                message = null
            )
                .updateState()
            serviceRepository.isConnected().collect {
                if (it) {
                    when (val result = remoteRepository.getCharacterById(id)) {
                        is ResultWork.Error -> {
                            _state.value.copy(
                                isLoading = false,
                                message = result.error.asUiText()
                            )
                                .updateState()
                        }
                        is ResultWork.Success -> {
                            _state.value.copy(
                                isLoading = false,
                                characterDetails = result.data
                            )
                                .updateState()
                        }
                    }
                } else {
                    when (val result = localRepository.getCharacterById(id)) {
                        is ResultWork.Error -> {
                            _state.value.copy(
                                isLoading = false,
                                message = result.error.asUiText()
                            )
                                .updateState()
                        }
                        is ResultWork.Success -> {
                            _state.value.copy(
                                isLoading = false,
                                characterDetails = result.data
                            )
                                .updateState()
                        }
                    }
                }
            }
        }
    }

    private fun CharacterDetailsScreenState.updateState() {
        _state.update {
            this
        }
    }
}