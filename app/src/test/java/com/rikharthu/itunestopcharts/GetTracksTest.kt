package com.rikharthu.itunestopcharts

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.TracksRepository
import com.rikharthu.itunestopcharts.features.tracks.GetTracks
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetTracksTest : UnitTest() {

    private lateinit var getTracks: GetTracks

    @Mock
    private lateinit var tracksRepository: TracksRepository

    @Before
    fun setUp() {
        getTracks = GetTracks(tracksRepository)
        given { tracksRepository.hotTracks(1) }.willReturn(Either.Right(listOf()))
    }

    @Test
    fun `should get data from repository`() {
        val result = runBlocking { getTracks.run(1) }
        //getTracks(GlobalScope,1)

        verify(tracksRepository).hotTracks(1)
        verifyNoMoreInteractions(tracksRepository)
        assertTrue(result.isRight)
        assertEquals(emptyList<Track>(), (result as Either.Right).b)
    }
}
