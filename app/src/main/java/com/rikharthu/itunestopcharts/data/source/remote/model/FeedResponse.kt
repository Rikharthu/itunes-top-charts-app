package com.rikharthu.itunestopcharts.data.source.remote.model

data class FeedResponse(
        val entry: List<TrackModel>,
        val author: AuthorModel,
        val rights: String,
        val title: String,
        val icon: String,
        val url: String,
        val id: String
)