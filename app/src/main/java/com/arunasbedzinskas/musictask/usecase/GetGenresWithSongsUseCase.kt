package com.arunasbedzinskas.musictask.usecase

import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.data.Genre
import com.arunasbedzinskas.musictask.dataaccess.GenresWithSongsDataAccess

fun interface GetGenresWithSongsUseCase {

    suspend operator fun invoke(): List<Genre>
}

internal class GetGenresWithSongsUseCaseImpl(
    private val dataAccess: GenresWithSongsDataAccess
) : GetGenresWithSongsUseCase {

    @WorkerThread
    override suspend fun invoke(): List<Genre> = dataAccess.getGenresWithSongs()
}
