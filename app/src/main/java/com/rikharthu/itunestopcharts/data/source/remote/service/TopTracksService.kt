package com.rikharthu.itunestopcharts.data.source.remote.service

import com.rikharthu.itunestopcharts.data.source.remote.model.FeedResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TopTracksService {

    @GET("/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit={count}/json")
    fun getTopTracks(@Path("count") count: Int): Deferred<Response<FeedResponse>>
}