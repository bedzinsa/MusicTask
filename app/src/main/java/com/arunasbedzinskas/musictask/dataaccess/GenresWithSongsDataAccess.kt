package com.arunasbedzinskas.musictask.dataaccess

import android.content.Context
import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.data.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun interface GenresWithSongsDataAccess {

    suspend fun getGenresWithSongs(): List<Genre>

}

internal class GenresWithSongsDataAccessImpl(
    private val appContext: Context,
    private val gson: Gson
) : GenresWithSongsDataAccess {

    private var genres: List<Genre> = listOf()

    @WorkerThread
    override suspend fun getGenresWithSongs(): List<Genre> {
        if (genres.isEmpty()) {
            fetchGenresWithSongs()
        }
        return genres
    }

    private fun fetchGenresWithSongs() {
        val jsonString = buildString {
            appContext.assets
                .open("genres_and_songs.json")
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach { append(it) }
                }
        }

        val listType = object : TypeToken<List<Genre>>() {}.type
        genres = gson.fromJson(jsonString, listType)
    }
}