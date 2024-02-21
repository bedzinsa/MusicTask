package com.arunasbedzinskas.musictask.dataaccess

import android.content.Context
import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.models.data.GenreDataModel
import com.arunasbedzinskas.musictask.models.data.GenresWithSongsDataModel
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import com.google.gson.Gson


interface GenresWithSongsDataAccess {

    suspend fun getGenresWithSongs(): List<GenreDataModel>

    suspend fun getAllSongs(): List<SongDataModel>

}

internal class GenresWithSongsDataAccessImpl(
    private val appContext: Context,
    private val gson: Gson
) : GenresWithSongsDataAccess {

    private var genres: List<GenreDataModel> = listOf()

    @WorkerThread
    override suspend fun getGenresWithSongs(): List<GenreDataModel> {
        fetchGenresWithSongs()
        return genres
    }

    @WorkerThread
    override suspend fun getAllSongs(): List<SongDataModel> {
        fetchGenresWithSongs()
        return genres.flatMap { genre -> genre.songs.map { it } }
    }

    private fun fetchGenresWithSongs() {
        if (genres.isNotEmpty()) return
        val jsonString = buildString {
            appContext.assets
                .open("genres_and_songs.json")
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach { append(it) }
                }
        }

        genres = gson.fromJson(jsonString, GenresWithSongsDataModel::class.java).genres
    }
}