package com.rikharthu.itunestopcharts.data.source

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.TrackEntity

interface TracksDataSource {

    fun getHotTracks(count: Int): Either<Failure, List<TrackEntity>>

    fun getTrack(id: String): Either<Failure, TrackEntity>

    fun saveTracks(vararg tracks: TrackEntity)

    fun getFavoriteTracks(): Either<Failure, List<TrackEntity>>

    fun addTrackToFavorites(trackId: String)

    fun removeTrackFromFavorites(trackId: String)
}