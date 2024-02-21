package com.arunasbedzinskas.musictask.usecase

import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.datastore.SongDataStore
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform

fun interface GetAllSongsUseCase {

    suspend operator fun invoke(): Flow<List<SongUIModel>>
}

internal class GetAllSongsUseCaseImpl(
    private val genresWithSongsDataAccess: GenresWithSongsDataAccess,
    private val mapSongUIUseCase: MapSongUIUseCase,

    private val songDataStore: SongDataStore,
    private val songDao: SongDao
) : GetAllSongsUseCase {

    override suspend fun invoke(): Flow<List<SongUIModel>> {
        val songsDataModels = genresWithSongsDataAccess.getAllSongs()

        return songDataStore.getSongs()
            .combineTransform(songDao.getAll()) { dataStoreSongs, daoSongs ->
                emit(
                    songsDataModels.map { songDataModel ->
                        mapSongUIUseCase(
                            songDataModel = songDataModel,
                            isSaved = dataStoreSongs.any { it.id == songDataModel.id }
                                    || daoSongs.any { it.songId == songDataModel.id }
                        )
                    }
                )
            }
    }
}
