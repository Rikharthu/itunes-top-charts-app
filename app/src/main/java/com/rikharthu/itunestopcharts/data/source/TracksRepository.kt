package com.rikharthu.itunestopcharts.data.source

import com.rikharthu.itunestopcharts.data.Track

interface TracksRepository {

    suspend fun getHotTracks(count: Int): Resource<List<Track>>

    suspend fun getFavoriteTracks(): Resource<List<Track>>

    suspend fun getTrack(trackId: String): Resource<Track>

    suspend fun saveTrack(track: Track)

    suspend fun favoriteTrack(trackId: String)

    suspend fun unFavoriteTrack(trackId: String)

    suspend fun deleteTrack(trackId: String)

    suspend fun deleteAllTracks()

    suspend fun refreshTracks()
}