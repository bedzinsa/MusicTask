package com.arunasbedzinskas.musictask.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true) val songId: Long,
    val name: String,
    val artist: String,
    val size: String, // KB, MB
    val length: String // mins, secs
)
