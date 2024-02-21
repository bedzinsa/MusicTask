package com.arunasbedzinskas.musictask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arunasbedzinskas.musictask.database.dao.SongDao
import com.arunasbedzinskas.musictask.database.entity.Song

@Database(
    entities = [Song::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        const val DB_NAME = "db-songs"
    }
}
