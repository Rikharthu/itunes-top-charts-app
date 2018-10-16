package com.rikharthu.itunestopcharts.browse

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rikharthu.itunestopcharts.App
import com.rikharthu.itunestopcharts.R
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.data.source.TracksRepositoryImpl
import com.rikharthu.itunestopcharts.event.TrackCountChangedEvent
import com.rikharthu.itunestopcharts.play.PlayerActivity
import kotlinx.android.synthetic.main.fragment_hot_charts.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


class FavoriteTracksFragment : Fragment(), KodeinAware {

    // TODO fade-out remove track if it is removed from favorites in this screen
    // TODO do not show position number in the top-left corner

    override lateinit var kodein: Kodein
    private val viewModel: HotChartsViewModel by instance()
    private val repository: TracksRepositoryImpl by instance()
    private lateinit var tracksAdapter: TracksAdapter
    var count = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein = App.get(context!!).kodein
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hot_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTracksList()

        launch(UI) {
            val tracks = repository.getFavoriteTracks()
            if (tracks is Resource.Success) {
                tracksAdapter.data.addAll(tracks.data)
                tracksAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupTracksList() {
        tracksAdapter = TracksAdapter()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        tracksList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(tracksList.context, layoutManager.orientation)
        tracksList.adapter = tracksAdapter
        tracksList.addItemDecoration(dividerItemDecoration)
        tracksAdapter.listener = object : TracksAdapter.OnTrackSelectedListener {
            override fun onTrackFavoriteClicked(track: Track) {
                launch(UI) {
                    if (track.isFavorite) {
                        repository.unFavoriteTrack(track.id)
                    } else {
                        repository.favoriteTrack(track.id)
                    }
                    val updatedTrack = repository.getTrack(track.id)
                    if (updatedTrack is Resource.Success) {
                        tracksAdapter.data.find {
                            it.id == track.id
                        }?.let {
                            val index = tracksAdapter.data.indexOf(it)
                            tracksAdapter.data[index] = updatedTrack.data
                            tracksAdapter.notifyItemChanged(index)
                        }
                    }
                }
            }

            override fun onTrackClicked(track: Track) {
                startActivity(Intent(context!!, PlayerActivity::class.java).apply {
                    putExtra(PlayerActivity.EXTRA_TRACK_ID, track.id)
                })
            }
        }
    }

    @Subscribe
    fun onTrackCountChangedEvent(event: TrackCountChangedEvent) {
        launch(UI) {
            val tracks = repository.getFavoriteTracks()
            if (tracks is Resource.Success) {
                tracksAdapter.data.clear()
                tracksAdapter.data.addAll(tracks.data)
                tracksAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
            inflater.inflate(R.menu.menu_browse, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                refreshCharts()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun refreshCharts() {
        launch(UI) {
            repository.refreshTracks()
            val tracks = repository.getFavoriteTracks()
            if (tracks is Resource.Success) {
                tracksAdapter.data.clear()
                tracksAdapter.data.addAll(tracks.data)
                tracksAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                FavoriteTracksFragment()
    }
}
