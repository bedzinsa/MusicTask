package com.arunasbedzinskas.musictask.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.arunasbedzinskas.musictask.database.entity.Song

@Dao
interface SongDao {

    @Query("SELECT * FROM Song LIMIT :limit")
    fun getAll(limit: Int = 0): List<Song>

    @Insert
    fun insert(song: Song): Long

    @Delete
    fun delete(song: Song): Int
}
