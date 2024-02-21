package com.arunasbedzinskas.musictask.usecase

import android.content.Context
import android.text.format.Formatter
import com.arunasbedzinskas.musictask.ext.toTrackLength
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import com.arunasbedzinskas.musictask.models.ui.SongUIModel
import java.time.Duration

fun interface MapSongUIUseCase {

    operator fun invoke(
        songDataModel: SongDataModel,
        isSaved: Boolean
    ): SongUIModel
}

internal class MapSongUIUseCaseImpl(private val appContext: Context) : MapSongUIUseCase {

    override fun invoke(
        songDataModel: SongDataModel,
        isSaved: Boolean
    ): SongUIModel = SongUIModel(
            id = songDataModel.id,
            name = songDataModel.name,
            artist = songDataModel.artist,
            imageUrl = songDataModel.imageUrl,
            size = Formatter.formatShortFileSize(appContext, songDataModel.size),
            length = Duration.ofSeconds(songDataModel.length).toTrackLength(),
            isSaved = isSaved
        )
}
