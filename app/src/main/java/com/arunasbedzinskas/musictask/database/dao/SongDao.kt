package com.arunasbedzinskas.musictask.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arunasbedzinskas.musictask.database.entity.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Query("SELECT * FROM Song")
    fun getAll(): Flow<List<Song>>

    @Insert
    suspend fun insert(song: Song): Long
}
