package com.rikharthu.itunestopcharts.data.source

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.Track

interface TracksRepository {

    fun hotTracks(count: Int): Either<Failure, List<Track>>

    fun favoriteTracks(): Either<Failure, List<Track>>

    fun track(id: String): Either<Failure, Track>

    fun save(track: Track)

    fun favorite(id:String)

    fun unfavorite(id:String)

    fun refresh()
}