package com.arunasbedzinskas.musictask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.models.data.StorageTypeDataModel
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel
import com.arunasbedzinskas.musictask.state.UiState
import com.arunasbedzinskas.musictask.usecase.GetLimitGenresUseCase
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val dispatchers: AppDispatchers,
    private val getLimitGenresUseCase: GetLimitGenresUseCase,
    private val getStorageTypesDataUseCase: GetStorageTypesDataUseCase
) : ViewModel() {

    private val _genresWithSongsFlow =
        MutableStateFlow<UiState<List<GenreUIModel>>>(UiState.LoadingState())
    val genresWithSongsFlow: StateFlow<UiState<List<GenreUIModel>>>
        get() = _genresWithSongsFlow.asStateFlow()

    private val _storageTypesFlow =
        MutableStateFlow<UiState<List<StorageTypeDataModel>>>(UiState.LoadingState())
    val storageTypesFlow: StateFlow<UiState<List<StorageTypeDataModel>>>
        get() = _storageTypesFlow.asStateFlow()

    init {
        fetchGenresWithSongs()
        fetchStorageTypesData()
    }

    private fun fetchGenresWithSongs() {
        viewModelScope.launch {
            _genresWithSongsFlow.value = withContext(dispatchers.io) {
                UiState.NormalState(getLimitGenresUseCase())
            }
        }
    }

    private fun fetchStorageTypesData() {
        viewModelScope.launch {
            getStorageTypesDataUseCase()
                .flowOn(dispatchers.io)
                .onEach {
                    _storageTypesFlow.value = UiState.NormalState(it)
                }
                .collect()
        }
    }
}
