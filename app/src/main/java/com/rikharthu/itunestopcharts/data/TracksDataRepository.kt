package com.rikharthu.itunestopcharts.data

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.core.functional.map
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.data.source.TracksRepository

class TracksDataRepository(private val remote: TracksDataSource,
                           private val local: TracksDataSource) : TracksRepository {

    private var isCacheDirty = false

    override fun hotTracks(count: Int): Either<Failure, List<Track>> {
        if (isCacheDirty) {
            return getTracksFromRemoteDataSource(count)
        }
        val localTracks = local.getHotTracks(count)
        return if (localTracks is Either.Right && localTracks.b.size >= count) {
            Either.Right(localTracks.b.map { it.toTrack() })
        } else {
            getTracksFromRemoteDataSource(count)
        }
    }

    override fun track(id: String): Either<Failure, Track> {
        return local.getTrack(id).map { it.toTrack() }
    }

    override fun save(track: Track) {
        local.saveTracks(track.toEntity())
    }

    override fun favorite(id: String) {
        local.addTrackToFavorites(id)
    }

    override fun unfavorite(id: String) {
        local.removeTrackFromFavorites(id)
    }

    override fun refresh() {
        isCacheDirty = true
    }

    override fun favoriteTracks(): Either<Failure, List<Track>> {
        return local.getFavoriteTracks().asTracks()
    }

    private fun getTracksFromRemoteDataSource(count: Int): Either<Failure, List<Track>> {
        val result = remote.getHotTracks(count)
        return if (result is Either.Right) {
            // save data and return local
            refreshLocalDataSource(result.b)
            local.getHotTracks(count).asTracks()
        } else {
            result.asTracks()
        }

    }

    private fun refreshLocalDataSource(newTracks: List<TrackEntity>) {
        // TODO pay attention to favorite status of existing track, since remote tracks cant be favorite
        local.saveTracks(*newTracks.toTypedArray())
        isCacheDirty = false
    }



    private fun <T> Either<T, List<TrackEntity>>.asTracks(): Either<T, List<Track>> {
        return this.map { tracks ->
            tracks.map { trackEntity -> trackEntity.toTrack() }
        }
    }
}

// TODO move to converters
fun TrackEntity.toTrack(): Track {
    return Track(this.name, this.image, this.collection, this.price, this.rights, this.title, this.id, this.artist,
            this.previewUrl, this.releaseDate, this.category, this.isHot, this.isFavorite)
}

// TODO move to converters
fun Track.toEntity(): TrackEntity {
    return TrackEntity(this.name, this.image, this.collection, this.price, this.rights, this.title, this.id, this.artist,
            this.previewUrl, this.releaseDate, this.category, this.isHot, this.isFavorite)
}