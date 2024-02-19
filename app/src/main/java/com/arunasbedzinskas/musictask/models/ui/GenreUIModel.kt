package com.arunasbedzinskas.musictask.models.ui

data class GenreUIModel(
    val id: Int,
    val name: String,
    val songs: List<SongUIModel>
)
