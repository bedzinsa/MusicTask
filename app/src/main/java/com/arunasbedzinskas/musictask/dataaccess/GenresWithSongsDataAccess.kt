package com.arunasbedzinskas.musictask.dataaccess

import android.content.Context
import androidx.annotation.WorkerThread
import com.arunasbedzinskas.musictask.models.data.GenreDataModel
import com.arunasbedzinskas.musictask.models.data.GenresWithSongsDataModel
import com.google.gson.Gson


fun interface GenresWithSongsDataAccess {

    suspend fun getGenresWithSongs(): List<GenreDataModel>

}

internal class GenresWithSongsDataAccessImpl(
    private val appContext: Context,
    private val gson: Gson
) : GenresWithSongsDataAccess {

    @WorkerThread
    override suspend fun getGenresWithSongs(): List<GenreDataModel> = fetchGenresWithSongs()

    private fun fetchGenresWithSongs(): List<GenreDataModel> {
        val jsonString = buildString {
            appContext.assets
                .open("genres_and_songs.json")
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach { append(it) }
                }
        }
        return gson.fromJson(jsonString, GenresWithSongsDataModel::class.java).genres
    }
}
