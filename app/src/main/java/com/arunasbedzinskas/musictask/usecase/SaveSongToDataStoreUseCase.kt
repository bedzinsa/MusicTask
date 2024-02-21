package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.datastore.MusicDataStore

fun interface SaveSongToDataStoreUseCase {

    suspend operator fun invoke(songId: Int)
}

internal class SaveSongToDataStoreUseCaseImpl(
    private val dataAccess: GenresWithSongsDataAccess,
    private val musicDataStore: MusicDataStore
) : SaveSongToDataStoreUseCase {

    override suspend fun invoke(songId: Int) {
        val songDataModel = dataAccess.getAllSongs().first { it.id == songId }
        musicDataStore.addSong(songDataModel)
    }
}