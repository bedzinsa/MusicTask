package com.arunasbedzinskas.musictask.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arunasbedzinskas.musictask.EMPTY_STRING
import com.arunasbedzinskas.musictask.R
import com.arunasbedzinskas.musictask.ext.koinActivityViewModel
import com.arunasbedzinskas.musictask.models.data.StorageTypeDataModel
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import com.arunasbedzinskas.musictask.state.UiState
import com.arunasbedzinskas.musictask.ui.components.ListHeadingItem
import com.arunasbedzinskas.musictask.ui.theme.AppTheme
import com.arunasbedzinskas.musictask.viewmodel.HomeViewModel
import com.arunasbedzinskas.musictask.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = koinActivityViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    mainViewModel.setTopBarTitle(EMPTY_STRING)
    val genresState by homeViewModel.fetchGenresWithSongs().collectAsState(
        initial = UiState.LoadingState()
    )
    val storageTypesState by homeViewModel.fetchStorageTypesData().collectAsState(
        initial = UiState.LoadingState()
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        genresWithSongsRows(genresState)
        storageTypesUI(storageTypesState)
    }
}

private fun LazyListScope.genresWithSongsRows(genresState: UiState<List<GenreUIModel>>) {
    when (genresState) {
        is UiState.LoadingState -> {}
        is UiState.NormalState -> {
            val genres = genresState.data
            items(genres.size) { index ->
                val genre = genres[index]
                GenreSection(
                    genre = genre,
                    onSeeAllClick = {} // TODO
                )
            }
        }
    }
}

private fun LazyListScope.storageTypesUI(storageTypesState: UiState<List<StorageTypeDataModel>>) {
    when (storageTypesState) {
        is UiState.LoadingState -> {}
        is UiState.NormalState -> {
            val storageTypesData = storageTypesState.data

            item { ListHeadingItem(heading = stringResource(R.string.home_section_storage)) }
            items(storageTypesData.size) { index ->
                val storageTypeData = storageTypesData[index]
                StorageType(storageTypeData) { /* TODO */ }
            }
        }
    }
}

@Composable
private fun GenreSection(
    genre: GenreUIModel,
    onSeeAllClick: () -> Unit
) {
    val songs = genre.songs
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ListHeadingItem(heading = genre.name) {
            OutlinedButton(onClick = onSeeAllClick) {
                Text(
                    text = stringResource(R.string.button_see_all),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(songs.size) { index ->
                if (index == 0) {
                    Spacer(Modifier.width(8.dp))
                }
                SongSection(songs[index])
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun SongSection(song: SongUIModel) {
    Card(
        Modifier
            .width(140.dp)
            .height(200.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = song.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = song.name,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = song.size,
                    modifier = Modifier.weight(1f, fill = false),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = song.length,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun StorageType(
    storageTypeData: StorageTypeDataModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = storageTypeData.storageType.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelLarge,
        )

        Text(
            text = storageTypeData.totalLength,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
            contentDescription = null, // FIXME
        )
    }
    HorizontalDivider()
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
