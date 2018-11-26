package com.rikharthu.itunestopcharts.data

data class TrackEntity(
        val name: String,
        val image: String,
        val collection: String,
        val price: String,
        val rights: String,
        val title: String,
        val id: String,
        val artist: String,
        val previewUrl: String,
        val releaseDate: String,
        val category: String,
        val isHot: Boolean,
        val isFavorite: Boolean
)