package com.arunasbedzinskas.musictask.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey val songId: Int,
    val name: String,
    val artist: String,
    val size: Long, // KB, MB
    val length: Long // mins, secs
)
