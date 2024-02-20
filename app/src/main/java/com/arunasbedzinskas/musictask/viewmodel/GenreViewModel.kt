package com.arunasbedzinskas.musictask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel
import com.arunasbedzinskas.musictask.state.UiState
import com.arunasbedzinskas.musictask.usecase.GetGenreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreViewModel(
    genreId: Int,
    private val getGenreUseCase: GetGenreUseCase,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _genreUIFlow = MutableStateFlow<UiState<GenreUIModel>>(UiState.LoadingState())
    val genreUIFlow: StateFlow<UiState<GenreUIModel>>
        get() = _genreUIFlow

   init {
       fetchGenre(genreId)
   }

    private fun fetchGenre(genreId: Int) {
        viewModelScope.launch {
            _genreUIFlow.value = withContext(dispatchers.io) {
                UiState.NormalState(getGenreUseCase(genreId))
            }
        }
    }
}
