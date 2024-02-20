package com.arunasbedzinskas.musictask.usecase

import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.models.ui.GenreUIModel

fun interface GetGenreUseCase {

    suspend operator fun invoke(genreId: Int): GenreUIModel
}

internal class GetGenreUseCaseImpl(
    private val getAllGenresUseCase: GetAllGenresUseCase
) : GetGenreUseCase {

    @WorkerThread
    override suspend fun invoke(genreId: Int): GenreUIModel = getAllGenresUseCase()
        .first { genre -> genre.id == genreId }
}
