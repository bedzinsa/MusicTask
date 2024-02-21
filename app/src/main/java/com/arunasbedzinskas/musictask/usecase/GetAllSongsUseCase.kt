package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.datastore.MusicDataStore
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun interface GetAllSongsUseCase {

    suspend operator fun invoke(): Flow<List<SongUIModel>>
}

internal class GetAllSongsUseCaseImpl(
    private val genresWithSongsDataAccess: GenresWithSongsDataAccess,
    private val mapSongUIUseCase: MapSongUIUseCase,
    private val musicDataStore: MusicDataStore
) : GetAllSongsUseCase {

    override suspend fun invoke(): Flow<List<SongUIModel>> {
        val songsDataModels = genresWithSongsDataAccess.getAllSongs()

        return musicDataStore.getSongs().transform { dataModels ->
            emit(
                songsDataModels.map { songDataModel ->
                    mapSongUIUseCase(
                        songDataModel,
                        isSaved = dataModels.any { it.id == songDataModel.id }
                    )
                }
            )
        }
    }
}
