package com.arunasbedzinskas.musictask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunasbedzinskas.musictask.dispatchers.AppDispatchers
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import com.arunasbedzinskas.musictask.usecase.GetAllSongsUseCase
import com.arunasbedzinskas.musictask.usecase.SaveSongToDataStoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StorageViewModel(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val saveSongToDataStoreUseCase: SaveSongToDataStoreUseCase,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _songsFlow = MutableStateFlow(listOf<SongUIModel>())
    val songsFlow: StateFlow<List<SongUIModel>>
        get() = _songsFlow.asStateFlow()

    init {
        fetchAllSongs()
    }

    fun saveSong(songUIModel: SongUIModel) {
        viewModelScope.launch {
            saveSongToDataStoreUseCase(songUIModel.id)
        }
    }

    private fun fetchAllSongs() {
        viewModelScope.launch {
            getAllSongsUseCase()
                .flowOn(dispatchers.io)
                .onEach { _songsFlow.value = it }
                .collect()
        }
    }
}
