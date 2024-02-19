package com.arunasbedzinskas.musictask.viewmodel

import androidx.lifecycle.ViewModel
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.state.UiState
import com.arunasbedzinskas.musictask.usecase.GetGenresWith5SongsUseCase
import com.arunasbedzinskas.musictask.usecase.GetStorageTypesDataUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val dispatchers: AppDispatchers,
    private val getGenresWith5SongsUseCase: GetGenresWith5SongsUseCase,
    private val getStorageTypesDataUseCase: GetStorageTypesDataUseCase
) : ViewModel() {

    fun fetchGenresWithSongs() = flow {
        emit(UiState.LoadingState())
        emit(
            withContext(dispatchers.io) {
                UiState.NormalState(getGenresWith5SongsUseCase())
            }
        )
    }

    fun fetchStorageTypesData() = flow {
        emit(UiState.LoadingState())
        emit(
            withContext(dispatchers.io) {
                UiState.NormalState(getStorageTypesDataUseCase())
            }
        )
    }
}
