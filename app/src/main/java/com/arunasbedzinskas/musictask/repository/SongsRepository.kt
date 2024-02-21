package com.arunasbedzinskas.musictask.repository

import android.util.Log
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.database.entity.Song
import com.arunasbedzinskas.musictask.datastore.SongDataStore
import com.arunasbedzinskas.musictask.models.data.SongDataModel
import kotlinx.coroutines.flow.firstOrNull

class SongsRepository(
    private val songDataStore: SongDataStore,
    private val songDao: SongDao
) {

    suspend fun saveSongsToDatabase(): Boolean {
        val songs = songDataStore.getSongs().firstOrNull() ?: return run {
            Log.i(TAG, "No songs saved to datastore")
            true
        }

        var isSuccess = false
        songs.forEach { song ->
            val entity = mapToEntity(song)

            isSuccess = isSuccess || insertSongToDb(entity)
            songDataStore.removeSong(song)
            Log.i(TAG, "Song removed from DataStore")
        }
        Log.i(TAG, "Finished saving songs to DAO")
        return isSuccess
    }

    private suspend fun insertSongToDb(entity: Song): Boolean {
        val isSongInserted = songDao.insert(entity) > 0
        if (isSongInserted) {
            Log.i(TAG, "Song inserted to DAO")
        } else {
            Log.e(TAG, "Failed to insert song to DAO")
        }
        return isSongInserted
    }

    private fun mapToEntity(songDataModel: SongDataModel) = Song(
        songId = songDataModel.id,
        name = songDataModel.name,
        artist = songDataModel.artist,
        length = songDataModel.length,
        size = songDataModel.size
    )

    companion object {

        private const val TAG = "SongsRepository"
    }
}
