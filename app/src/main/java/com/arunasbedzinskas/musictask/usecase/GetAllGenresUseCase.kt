package com.arunasbedzinskas.musictask.usecase

import android.content.Context
import android.text.format.Formatter.formatShortFileSize
import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess
import com.arunasbedzinskas.musictask.ext.toTrackLength
import com.arunasbedzinskas.musictask.models.data.GenreDataModel
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import java.time.Duration

fun interface GetAllGenresUseCase {

    suspend operator fun invoke(): List<GenreUIModel>
}

internal class GetAllGenresUseCaseImpl(
    private val appContext: Context,
    private val dataAccess: GenresWithSongsDataAccess
) : GetAllGenresUseCase {

    @WorkerThread
    override suspend fun invoke(): List<GenreUIModel> = dataAccess.getGenresWithSongs()
        .map { genreDataModel ->
            mapGenreUI(genreDataModel) { songDataModel ->
                mapSongUI(songDataModel)
            }
        }

    private fun mapGenreUI(
        genreDataModel: GenreDataModel,
        songMapper: (SongDataModel) -> SongUIModel
    ): GenreUIModel = GenreUIModel(
        id = genreDataModel.id,
        name = genreDataModel.name,
        songs = genreDataModel.songs.map { songMapper(it) }
    )

    private fun mapSongUI(songDataModel: SongDataModel) = SongUIModel(
        id = songDataModel.id,
        name = songDataModel.name,
        artist = songDataModel.artist,
        imageUrl = songDataModel.imageUrl,
        size = formatShortFileSize(appContext, songDataModel.size),
        length = Duration.ofSeconds(songDataModel.length).toTrackLength()
    )
}
