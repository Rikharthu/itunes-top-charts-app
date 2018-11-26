package com.rikharthu.itunestopcharts.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.functional.Either
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.TracksRepository
import com.rikharthu.itunestopcharts.features.tracks.GetTracks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

/*
Since viewModelJob is passed as the job to uiScope, when viewModelJob is cancelled
every coroutine started by uiScope will be cancelled as well. It's important to
cancel any coroutines that are no longer required to avoid unnecessary work and
memory leaks.
 */

class HotChartsViewModel : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // TODO move to @Inject constructor parameters
    private val getTracks = GetTracks(object : TracksRepository {
        override fun track(id: String): Either<Failure, Track> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun save(track: Track) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun favorite(id: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun unfavorite(id: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun refresh() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun hotTracks(count: Int): Either<Failure, List<Track>> {
            val tracks = mutableListOf<Track>()
            for (i in 0 until count) {
                tracks.add(Track("Shallow", "https://is3-ssl.mzstatic.com/image/thumb/Music118/v4/14/52/12/145212ba-1fa2-5924-2268-8652cc693c22/00602577014284.rgb.jpg/170x170bb-85.png",
                        "Test collection", "test price", "test rights", "title", "id", "artist",
                        "https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001/AudioPreview128/v4/03/78/b5/0378b505-0651-5d37-abaf-fdace32fa0a1/mzaf_4885861497912518049.plus.aac.p.m4a",
                        "2018-10-05T00:00:00-07:00", "category", true, true))
            }
            return Either.Right(tracks)
        }

        override fun favoriteTracks(): Either<Failure, List<Track>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })

    fun loadTracks(count: Int) = getTracks(uiScope, count) { it.either(::handleFailure, ::handleTracksList) }

    private fun handleFailure(failure: Failure) {
        Timber.e("Could not load tracks: $failure")
    }

    private fun handleTracksList(newTracks: List<Track>) {
        Timber.e("Tracks: $newTracks")
        _tracks.value = newTracks
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}