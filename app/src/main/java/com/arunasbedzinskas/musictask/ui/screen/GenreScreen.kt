package com.arunasbedzinskas.musictask.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel
import com.arunasbedzinskas.musictask.state.UiState
import com.arunasbedzinskas.musictask.ui.components.MultiLineListItem
import com.arunasbedzinskas.musictask.viewmodel.GenreViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GenreScreen(
    genreId: Int,
    genreViewModel: GenreViewModel = koinViewModel(parameters = { parametersOf(genreId) })
) {
    val genreState by genreViewModel.genreUIFlow.collectAsState()
    GenreWithSongsContent(genreState)
}

@Composable
private fun GenreWithSongsContent(genreState: UiState<GenreUIModel>) {
    if (genreState !is UiState.NormalState) return
    val genreUIModel = genreState.data

    val songs = genreUIModel.songs
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(songs.size) { index ->
            val song = songs[index]
            MultiLineListItem(
                firstLineText = "${song.artist} - ${song.name}",
                secondLineText = "${song.size} - ${song.length}"
            )
        }
    }
}
