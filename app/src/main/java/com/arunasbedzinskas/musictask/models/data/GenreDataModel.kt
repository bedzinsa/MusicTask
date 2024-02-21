package com.arunasbedzinskas.musictask.models.data

data class GenreDataModel(
    val id: Int,
    val name: String,
    val songs: List<SongDataModel>
)
