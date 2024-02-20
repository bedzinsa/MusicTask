package com.arunasbedzinskas.musictask.usecase

import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel

fun interface GetLimitGenresUseCase {

    suspend operator fun invoke(): List<GenreUIModel>
}

internal class GetLimitGenresUseCaseImpl(
    private val getAllGenresUseCase: GetAllGenresUseCase
) : GetLimitGenresUseCase {

    @WorkerThread
    override suspend fun invoke(): List<GenreUIModel> = getAllGenresUseCase()
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
