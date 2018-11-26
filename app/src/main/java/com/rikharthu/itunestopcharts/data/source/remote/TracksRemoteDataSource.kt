package com.rikharthu.itunestopcharts.data.source.remote

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.TrackEntity
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.data.source.remote.service.TopTracksService

class TracksRemoteDataSource(private val api: TopTracksService) : TracksDataSource {
    override fun getHotTracks(count: Int): Either<Failure, List<TrackEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTrack(id: String): Either<Failure, TrackEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTracks(vararg tracks: TrackEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoriteTracks(): Either<Failure, List<TrackEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrackToFavorites(trackId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTrackFromFavorites(trackId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override suspend fun getHotTracks(count: Int): Resource<List<Track>> {
//        val response = api.getTopTracks(count).await()
//        val body = response.body()
//        return if (response.isSuccessful && body != null) {
//            Resource.Success(body.entry.map {
//                it.asTrack()
//            })
//        } else {
//            Resource.Error(Throwable("Error fetching hotTracks"))
//        }
//    }
//
//    override suspend fun getFavoriteTracks(): Resource<List<Track>> {
//        throw UnsupportedOperationException("Can't get favorite hotTracks")
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
//    override suspend fun saveTracks(tracks: List<Track>) {
//        throw UnsupportedOperationException("Can't save hotTracks")
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


//    override suspend fun deleteAllTracks() {
//        throw UnsupportedOperationException("Can't delete hotTracks")
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
//            Resource.Error(Throwable("Error fetching hotTracks"))
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