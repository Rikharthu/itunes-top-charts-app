package com.rikharthu.itunestopcharts.data.source.local

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.executors.ioContext
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.TrackEntity
import com.rikharthu.itunestopcharts.data.source.LocalDataNotFoundException
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import kotlinx.coroutines.withContext
import timber.log.Timber

class TracksLocalDataSource constructor(
        val tracksDao: TracksDao
) : TracksDataSource {
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

//    override suspend fun getFavoriteTracks(): Resource<List<Track>> = withContext(ioContext) {
//        val tracks = tracksDao.getFavoriteTracks()
//        if (tracks.isNotEmpty()) {
//            Resource.Success(tracks.map { it.asTrack() })
//        } else {
//            Resource.Error(LocalDataNotFoundException())
//        }
//    }
//
//    override suspend fun getHotTracks(count: Int): Resource<List<Track>> = withContext(ioContext) {
//        val tracks = tracksDao.getHotTracks(count)
//        if (tracks.isNotEmpty()) {
//            Resource.Success(tracks.map { it.asTrack() })
//        } else {
//            Resource.Error(LocalDataNotFoundException())
//        }
//    }
//
//    override suspend fun getTrack(trackId: String): Resource<Track> = withContext(ioContext) {
//        val trackEntity = tracksDao.getTrackById(trackId)
//        if (trackEntity == null) {
//            Resource.Error(LocalDataNotFoundException())
//        } else {
//            Resource.Success(trackEntity.asTrack())
//        }
//    }
//
//    override suspend fun saveTrack(track: Track) = withContext(ioContext) {
//        tracksDao.insertTracks(track.asEntity())
//    }
//
//    override suspend fun saveTracks(tracks: List<Track>) = withContext(ioContext) {
//        val favoriteTracks = tracksDao.getFavoriteTracks().map { it.id }
//        Timber.d("Favorite hotTracks: $favoriteTracks")
//        val deletedTracksCount = tracksDao.deleteNotFavoriteTracks()
//        Timber.d("Deleted $deletedTracksCount not-favorite hotTracks")
//        val notHotCount = tracksDao.setNotHotTracks()
//        Timber.d("Set $notHotCount hotTracks as not-hot")
//        tracks.forEach { track ->
//            val wasFavorite = favoriteTracks.contains(track.id)
//            tracksDao.insertTracks(track.asEntity())
//            if (wasFavorite) {
//                tracksDao.setFavoriteStatus(true, track.id)
//            }
//        }
//        Unit
//    }
//
//    override suspend fun favoriteTrack(trackId: String) = withContext(ioContext) {
//        tracksDao.setFavoriteStatus(true, trackId)
//    }
//
//    override suspend fun unFavoriteTrack(trackId: String) = withContext(ioContext) {
//        tracksDao.setFavoriteStatus(false, trackId)
//
//    }
//
//    override suspend fun deleteTrack(trackId: String) {
//        tracksDao.deleteTrackById(trackId)
//    }

    /*
    override suspend fun getTracks(count: Int): Resource<List<Track>> = withContext(appExecutors.ioContext) {
        val hotTracks = tracksDao.getHotTracks(count)
//        val hotTracks = tracksDao.getFavoriteTracks()
        if (hotTracks.isNotEmpty()) {
            Resource.Success(hotTracks.map { it.asTrack() })
        } else {
            Resource.Error(LocalDataNotFoundException())
        }
    }

    override suspend fun getTrack(trackId: String): Resource<Track> = withContext(appExecutors.ioContext) {
        val trackEntity = tracksDao.getTrackById(trackId)
        if (trackEntity == null) {
            Resource.Error(LocalDataNotFoundException())
        } else {
            Resource.Success(trackEntity.asTrack())
        }
    }

    override suspend fun saveTrack(track: Track) = withContext(appExecutors.ioContext) {
        tracksDao.insertTracks(track.asEntity())
    }

    override suspend fun favoriteTrack(trackId: String) = withContext(appExecutors.ioContext) {
        tracksDao.setFavoriteStatus(true, trackId)
    }

    override suspend fun unFavoriteTrack(trackId: String) = withContext(appExecutors.ioContext) {
        tracksDao.setFavoriteStatus(false, trackId)
    }

    override suspend fun deleteTrack(trackId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun refreshTracks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllTracks(): Unit = withContext(appExecutors.ioContext) {
        val count = tracksDao.deleteNotFavoriteTracks()
        Timber.d("Deleted $count hotTracks")
        Unit
    }

    suspend fun saveTracks(hotTracks: List<Track>): Unit = withContext(appExecutors.ioContext) {
        val favoriteTracks = tracksDao.getFavoriteTracks().map { it.id }
        Timber.d("Favorite hotTracks: $favoriteTracks")
        val count = tracksDao.deleteNotFavoriteTracks()
        Timber.d("Deleted $count not-favorite hotTracks")
        val notHot = tracksDao.setNotHotTracks()
        Timber.d("Set $notHot hotTracks as not-hot")
        hotTracks.forEach { track ->
            val wasFavorite = favoriteTracks.contains(track.id)
            tracksDao.insertTracks(track.asEntity())
            if (wasFavorite) {
                tracksDao.setFavoriteStatus(true, track.id)
            }
        }
        Unit
    }
    */
}