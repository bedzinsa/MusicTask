package com.arunasbedzinskas.musictask.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TODO needs testing
class MusicDataStore(
    private val appContext: Context,
    private val gson: Gson
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(NAME)

    fun getSongs(): Flow<List<SongDataModel>> = appContext.dataStore.data.map { preference ->
        preference[songsKey]?.deserializeSet() ?: listOf()
    }

    suspend fun addSong(songUIModel: SongDataModel) {
        appContext.dataStore.edit { preferences ->
            val songs = preferences[songsKey]
                ?.deserializeSet<SongDataModel>()
                ?.toMutableList()
                ?: mutableListOf()
            songs.add(songUIModel)

            preferences[songsKey] = songs.serializeToSet()
        }
    }

    suspend fun removeSong(songUIModel: SongDataModel) {
        appContext.dataStore.edit { preferences ->
            val songs = preferences[songsKey]
                ?.deserializeSet<SongDataModel>()
                ?.toMutableList()
                ?: mutableListOf()
            songs.remove(songUIModel)

            preferences[songsKey] = songs.serializeToSet()
        }
    }

    private fun List<*>.serializeToSet(): Set<String> =
        map { gson.toJson(it) }.toSet()

    private inline fun <reified T : Any> Set<String>.deserializeSet(): List<T> =
        map { gson.fromJson(it, T::class.java) }

    companion object {
        private const val NAME = "SongDataStore"
        private const val KEY_NAME_SONGS = "Songs"

        private val songsKey = stringSetPreferencesKey(KEY_NAME_SONGS)
    }
}
