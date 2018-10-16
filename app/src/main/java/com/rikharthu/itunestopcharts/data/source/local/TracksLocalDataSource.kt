package com.rikharthu.itunestopcharts.data.source.local

import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.LocalDataNotFoundException
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.util.AppExecutors
import kotlinx.coroutines.experimental.withContext
import timber.log.Timber

class TracksLocalDataSource constructor(
        val tracksDao: TracksDao,
        val appExecutors: AppExecutors
) : TracksDataSource {

    override suspend fun getFavoriteTracks(): Resource<List<Track>> = withContext(appExecutors.ioContext) {
        val tracks = tracksDao.getFavoriteTracks()
        if (tracks.isNotEmpty()) {
            Resource.Success(tracks.map { it.asTrack() })
        } else {
            Resource.Error(LocalDataNotFoundException())
        }
    }

    override suspend fun getHotTracks(count: Int): Resource<List<Track>> = withContext(appExecutors.ioContext) {
        val tracks = tracksDao.getHotTracks(count)
        if (tracks.isNotEmpty()) {
            Resource.Success(tracks.map { it.asTrack() })
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

    override suspend fun saveTracks(tracks: List<Track>) = withContext(appExecutors.ioContext) {
        val favoriteTracks = tracksDao.getFavoriteTracks().map { it.id }
        Timber.d("Favorite tracks: $favoriteTracks")
        val deletedTracksCount = tracksDao.deleteNotFavoriteTracks()
        Timber.d("Deleted $deletedTracksCount not-favorite tracks")
        val notHotCount = tracksDao.setNotHotTracks()
        Timber.d("Set $notHotCount tracks as not-hot")
        tracks.forEach { track ->
            val wasFavorite = favoriteTracks.contains(track.id)
            tracksDao.insertTracks(track.asEntity())
            if (wasFavorite) {
                tracksDao.setFavoriteStatus(true, track.id)
            }
        }
        Unit
    }

    override suspend fun favoriteTrack(trackId: String) = withContext(appExecutors.ioContext) {
        tracksDao.setFavoriteStatus(true, trackId)
    }

    override suspend fun unFavoriteTrack(trackId: String) = withContext(appExecutors.ioContext) {
        tracksDao.setFavoriteStatus(false, trackId)

    }

    override suspend fun deleteTrack(trackId: String) {
        tracksDao.deleteTrackById(trackId)
    }

    /*
    override suspend fun getTracks(count: Int): Resource<List<Track>> = withContext(appExecutors.ioContext) {
        val tracks = tracksDao.getHotTracks(count)
//        val tracks = tracksDao.getFavoriteTracks()
        if (tracks.isNotEmpty()) {
            Resource.Success(tracks.map { it.asTrack() })
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
        Timber.d("Deleted $count tracks")
        Unit
    }

    suspend fun saveTracks(tracks: List<Track>): Unit = withContext(appExecutors.ioContext) {
        val favoriteTracks = tracksDao.getFavoriteTracks().map { it.id }
        Timber.d("Favorite tracks: $favoriteTracks")
        val count = tracksDao.deleteNotFavoriteTracks()
        Timber.d("Deleted $count not-favorite tracks")
        val notHot = tracksDao.setNotHotTracks()
        Timber.d("Set $notHot tracks as not-hot")
        tracks.forEach { track ->
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