package com.rikharthu.itunestopcharts.data.source

import com.rikharthu.itunestopcharts.data.Track
import timber.log.Timber

class TracksRepositoryImpl(
        private val remote: TracksDataSource,
        private val local: TracksDataSource
) {

    private var isCacheDirty = false

    suspend fun getHotTracks(count: Int): Resource<List<Track>> {
        val localTracksData = local.getHotTracks(count)
        return if (!isCacheDirty &&
                localTracksData is Resource.Success &&
                localTracksData.data.size == count) {
            Timber.tag("XDEBUG").d("Returning local $count tracks")
            localTracksData
        } else {
            Timber.tag("XDEBUG").d("Fetching $count tracks from network")
            getTracksFromRemoteDataSource(count)
        }
    }

    suspend fun getFavoriteTracks(): Resource<List<Track>> = local.getFavoriteTracks()

    private suspend fun getTracksFromRemoteDataSource(count: Int): Resource<List<Track>> {
        val result = remote.getHotTracks(count)
        return when (result) {
            is Resource.Success -> {
                refreshLocalDataSource(result.data)
                return local.getHotTracks(count)
            }
            is Resource.Error -> {
                result
            }
        }
    }

    private suspend fun refreshLocalDataSource(tracks: List<Track>) {
        local.saveTracks(tracks)
        isCacheDirty = false
    }

    suspend fun getTrack(trackId: String): Resource<Track> = local.getTrack(trackId)

    suspend fun saveTrack(track: Track) = local.saveTrack(track)

    suspend fun favoriteTrack(trackId: String) {
        local.favoriteTrack(trackId)
    }

    suspend fun unFavoriteTrack(trackId: String) {
        local.unFavoriteTrack(trackId)
    }

    suspend fun refreshTracks() {
        isCacheDirty = true
    }

//    override suspend fun getTracks(count: Int): Resource<List<Track>> {
//        val localTracksData = local.getTracks(count)
//        return if (!isCacheDirty &&
//                localTracksData is Resource.Success &&
//                localTracksData.data.size == count) {
//            Timber.tag("XDEBUG").d("Returning local $count tracks")
//            localTracksData
//        } else {
//            Timber.tag("XDEBUG").d("Fetching $count tracks from network")
//            getTracksFromRemoteDataSource(count)
//        }
//    }
//
//    private suspend fun getTracksFromRemoteDataSource(count: Int): Resource<List<Track>> {
//        val result = remote.getTracks(count)
//        return when (result) {
//            is Resource.Success -> {
//                refreshLocalDataSource(result.data)
//                return local.getTracks(count)
//            }
//            is Resource.Error -> {
//                result
//            }
//        }
//    }
//
//    private suspend fun refreshLocalDataSource(tracks: List<Track>) {
//        // TODO delete not favorite tracks
//        // TODO set isHot=false for favorite tracks
//        // TODO insert new tracks and mark them as hot (isHot=true)
//        // TODO if new track is already present and is favorite, then mark it as host and update values (check by id)
//        (local as TracksLocalDataSource).saveTracks(tracks)
//        isCacheDirty = false
//    }
//
//    override suspend fun getTrack(trackId: String): Resource<Track> = local.getTrack(trackId)
//
//    override suspend fun saveTrack(track: Track) = local.saveTrack(track)
//
//    override suspend fun favoriteTrack(trackId: String) {
//        local.favoriteTrack(trackId)
//    }
//
//    override suspend fun unFavoriteTrack(trackId: String) {
//        local.unFavoriteTrack(trackId)
//    }
//
//    override suspend fun deleteTrack(trackId: String) =
//            local.deleteTrack(trackId)
//
//    override suspend fun deleteAllTracks() =
//            local.deleteAllTracks()
//
//    // TODO not needed to suspend
//    override suspend fun refreshTracks() {
//        isCacheDirty = true
//    }
}