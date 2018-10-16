package com.rikharthu.itunestopcharts.data.source.remote

import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.data.source.remote.service.TopTracksService

class TracksRemoteDataSource(private val api: TopTracksService) : TracksDataSource {

    override suspend fun getHotTracks(count: Int): Resource<List<Track>> {
        val response = api.getTopTracks(count).await()
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Resource.Success(body.entry.map {
                it.asTrack()
            })
        } else {
            Resource.Error(Throwable("Error fetching tracks"))
        }
    }

    override suspend fun getFavoriteTracks(): Resource<List<Track>> {
        throw UnsupportedOperationException("Can't get favorite tracks")
    }

    override suspend fun getTrack(trackId: String): Resource<Track> {
        throw UnsupportedOperationException("Can't get single track")
    }

    override suspend fun saveTrack(track: Track) {
        throw UnsupportedOperationException("Can't save track")
    }

    override suspend fun saveTracks(tracks: List<Track>) {
        throw UnsupportedOperationException("Can't save tracks")
    }

    override suspend fun favoriteTrack(trackId: String) {
        throw UnsupportedOperationException("Can't favorite track")
    }

    override suspend fun unFavoriteTrack(trackId: String) {
        throw UnsupportedOperationException("Can't unfavorite track")
    }

    override suspend fun deleteTrack(trackId: String) {
        throw UnsupportedOperationException("Can't delete track")
    }


//    override suspend fun deleteAllTracks() {
//        throw UnsupportedOperationException("Can't delete tracks")
//    }
//
//    override suspend fun getTracks(count: Int): Resource<List<Track>> {
//        val response = api.getTopTracks(count).await()
//        val body = response.body()
//        return if (response.isSuccessful && body != null) {
//            Resource.Success(body.entry.map {
//                it.asTrack()
//            })
//        } else {
//            Resource.Error(Throwable("Error fetching tracks"))
//        }
//    }
//
//    override suspend fun getTrack(trackId: String): Resource<Track> {
//        throw UnsupportedOperationException("Can't get single track")
//    }
//
//    override suspend fun saveTrack(track: Track) {
//        throw UnsupportedOperationException("Can't save track")
//    }
//
//    override suspend fun favoriteTrack(trackId: String) {
//        throw UnsupportedOperationException("Can't favorite track")
//    }
//
//    override suspend fun unFavoriteTrack(trackId: String) {
//        throw UnsupportedOperationException("Can't unfavorite track")
//    }
//
//    override suspend fun deleteTrack(trackId: String) {
//        throw UnsupportedOperationException("Can't delete track")
//    }
//
//    override suspend fun refreshTracks() {
//    }
}