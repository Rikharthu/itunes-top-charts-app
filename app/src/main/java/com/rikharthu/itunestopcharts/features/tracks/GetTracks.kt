package com.rikharthu.itunestopcharts.features.tracks

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.core.interactor.UseCase
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.TracksRepository

class GetTracks
constructor(private val tracksRepository: TracksRepository) : UseCase<List<Track>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<Track>> = tracksRepository.hotTracks(params)
}