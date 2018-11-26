package com.rikharthu.itunestopcharts

import com.nhaarman.mockitokotlin2.*
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.TrackEntity
import com.rikharthu.itunestopcharts.data.TracksDataRepository
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.data.toTrack
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class TracksRepositoryTest : UnitTest() {

    private lateinit var tracksDataRepository: TracksDataRepository

    @Mock
    private lateinit var local: TracksDataSource
    @Mock
    private lateinit var remote: TracksDataSource

    @Before
    fun setUp() {
        tracksDataRepository = TracksDataRepository(remote, local)
    }

    @Test
    fun `should get data from repository`() {
        val trackCount = 10
        val trackEntities = (1..trackCount).map {
            TrackEntity("", "", "",
                    "", "", "", it.toString(),
                    "", "",
                    "", "",
                    false, false)
        }
        given { local.getHotTracks(trackCount) }.willReturn(Either.Right(trackEntities))
        val tracksResult = tracksDataRepository.hotTracks(trackCount)
        verify(local).getHotTracks(trackCount)
        verify(remote, never()).getHotTracks(any())

        assertTrue(tracksResult.isRight)
        val tracksData = (tracksResult as Either.Right).b
        assertEquals(trackEntities.map { it.toTrack() }, tracksData)
    }

    @Test
    fun `should refresh data from remote`() {
        val trackCount = 10
        val trackEntities = (1..trackCount).map {
            TrackEntity("Track #$it", "", "",
                    "", "", "", it.toString(),
                    "", "",
                    "", "",
                    false, false)
        }
        tracksDataRepository.refresh()
        given { remote.getHotTracks(trackCount) }.willReturn(Either.Right(trackEntities))
        given { local.getHotTracks(trackCount) }.willReturn(Either.Right(trackEntities))

        val tracksResult = tracksDataRepository.hotTracks(trackCount)
        // New tracks have been fetched from the remote data source
        verify(remote).getHotTracks(trackCount)
        // Data has been saved locally
        argumentCaptor<TrackEntity>().apply {
            verify(local).saveTracks(capture())
            val data = this.allValues
            assertEquals(trackEntities, data)
        }
        // New local data has been returned
        verify(local).getHotTracks(trackCount)

        assertTrue(tracksResult.isRight)
        val tracksData = (tracksResult as Either.Right).b
        assertEquals(trackEntities.map { it.toTrack() }, tracksData)
    }

    @Test
    fun `should refresh data from remote if not enough tracks stored locally`() {
        val localTrackCount = 10
        val remoteTrackCount = 20
        val localTrackEntities = (1..localTrackCount).map {
            TrackEntity("Local track #$it", "", "",
                    "", "", "", it.toString(),
                    "", "",
                    "", "",
                    false, false)
        }
        val remoteTrackEntities = (1..remoteTrackCount).map {
            TrackEntity("Remote track #$it", "", "",
                    "", "", "", it.toString(),
                    "", "",
                    "", "",
                    false, false)
        }
        given { remote.getHotTracks(remoteTrackCount) }.willReturn(Either.Right(remoteTrackEntities))
        given { local.getHotTracks(remoteTrackCount) }.willReturn(Either.Right(localTrackEntities))

        val tracksResult = tracksDataRepository.hotTracks(remoteTrackEntities.size)

        // New tracks have been fetched from the remote data source
        verify(remote).getHotTracks(remoteTrackEntities.size)
        // New data has been saved locally
        argumentCaptor<TrackEntity>().apply {
            verify(local).saveTracks(capture())
            val data = this.allValues
            assertEquals(remoteTrackEntities, data)
//            given { local.getHotTracks(remoteTrackCount) }.willReturn(Either.Right(data))
        }
        // New local data has been returned
        verify(local, times(2)).getHotTracks(remoteTrackCount)
    }
}
