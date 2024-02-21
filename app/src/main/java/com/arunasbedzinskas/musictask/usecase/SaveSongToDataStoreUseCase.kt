package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.datastore.SongDataStore

fun interface SaveSongToDataStoreUseCase {

    suspend operator fun invoke(songId: Int)
}

internal class SaveSongToDataStoreUseCaseImpl(
    private val dataAccess: GenresWithSongsDataAccess,
    private val songDataStore: SongDataStore
) : SaveSongToDataStoreUseCase {

    override suspend fun invoke(songId: Int) {
        val songDataModel = dataAccess.getAllSongs().firstOrNull { it.id == songId }
        songDataModel?.let {
            songDataStore.addSong(songDataModel)
        }
    }
}
