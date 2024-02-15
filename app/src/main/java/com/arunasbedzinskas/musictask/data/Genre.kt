package com.arunasbedzinskas.musictask.data

data class Genre(
    val id: Int,
    val name: String,
    val songs: List<Song>
)
