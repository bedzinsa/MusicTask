package com.arunasbedzinskas.musictask.usecase

import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel

fun interface GetGenresWith5SongsUseCase {

    suspend operator fun invoke(): List<GenreUIModel>
}

internal class GetGenresWith5SongsUseCaseImpl(
    private val getGenresWithSongsUseCase: GetGenresWithSongsUseCase
) : GetGenresWith5SongsUseCase {

    @WorkerThread
    override suspend fun invoke(): List<GenreUIModel> = getGenresWithSongsUseCase()
        .map { genre ->
            genre.copy(
                songs = genre.songs.filterIndexed { index, _ ->
                    index < LIMIT
                }
            )
        }

    companion object {

        private const val LIMIT = 5
    }
}
