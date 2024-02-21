package com.arunasbedzinskas.musictask.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.arunasbedzinskas.musictask.R
import com.arunasbedzinskas.musictask.models.enums.StorageType
import com.arunasbedzinskas.musictask.ui.components.MultiLineListItem
import com.arunasbedzinskas.musictask.viewmodel.StorageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StorageScreen(
    storageType: StorageType,
    storageViewModel: StorageViewModel = koinViewModel()
) {
    val songs by storageViewModel.songsFlow.collectAsState()

    LazyColumn {
        items(songs.size) { index ->
            val song = songs[index]
            val icon = if (song.isSaved)
                rememberVectorPainter(Icons.Default.Check)
            else
                painterResource(R.drawable.ic_save)
            val iconContentDesc = if (song.isSaved)
                stringResource(R.string.content_description_storage_song_saved)
            else
                stringResource(R.string.content_description_storage_song_non_saved)

            MultiLineListItem(
                firstLineText = "${song.artist} - ${song.name}",
                secondLineText = "${song.size} - ${song.length}",
                endIconButtonPainter = icon,
                endIconButtonContentDesc = iconContentDesc,
                onIconButtonClick = { storageViewModel.saveSong(song) },
                endIconButtonClickEnabled = storageType == StorageType.Memory && !song.isSaved
            )
        }
    }
}
