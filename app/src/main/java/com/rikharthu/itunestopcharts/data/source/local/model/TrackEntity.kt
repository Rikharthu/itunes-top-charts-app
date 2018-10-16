package com.rikharthu.itunestopcharts.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
        @PrimaryKey
        val id: String,
        val name: String,
        val image: String,
        val collection: String,
        val price: String,
        val rights: String,
        val title: String,
        val artist: String,
        @ColumnInfo(name = "preview_url")
        val previewUrl: String,
        val releaseDate: String,
        val category: String,
        val isHot: Boolean,
        val isFavorite: Boolean)