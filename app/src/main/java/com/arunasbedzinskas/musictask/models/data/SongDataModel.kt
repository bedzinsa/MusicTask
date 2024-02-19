package com.arunasbedzinskas.musictask.models.data

data class SongDataModel(
    val id: Int,
    val name: String,
    val artist: String,
    val imageUrl: String,
    val size: Long,
    val length: Long
)
