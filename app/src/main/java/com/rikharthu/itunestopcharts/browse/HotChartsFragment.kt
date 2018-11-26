package com.rikharthu.itunestopcharts.browse

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rikharthu.itunestopcharts.R
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rikharthu.itunestopcharts.App
import com.rikharthu.itunestopcharts.core.executors.uiContext
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.event.TrackCountChangedEvent
import com.rikharthu.itunestopcharts.play.PlayerActivity
import kotlinx.android.synthetic.main.fragment_hot_charts.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


class HotChartsFragment : Fragment(), KodeinAware {

    override lateinit var kodein: Kodein
    private lateinit var viewModel: HotChartsViewModel
//    private val repository: TracksRepositoryImpl by instance()
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
        viewModel = ViewModelProviders.of(this).get(HotChartsViewModel::class.java)

        GlobalScope.launch(uiContext) {
            //            val hotTracks = repository.getHotTracks(count)
//            if (hotTracks is Resource.Success) {
//                tracksAdapter.data.addAll(hotTracks.data)
//                tracksAdapter.notifyDataSetChanged()
//            }
        }
        viewModel.loadTracks(10)
        viewModel.tracks.observe(this, Observer {
            tracksAdapter.data.addAll(it)
            tracksAdapter.notifyDataSetChanged()
        })
    }

    private fun setupTracksList() {
        tracksAdapter = TracksAdapter()
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        tracksList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(tracksList.context, layoutManager.orientation)
        tracksList.adapter = tracksAdapter
        tracksList.addItemDecoration(dividerItemDecoration)
        tracksAdapter.listener = object : TracksAdapter.OnTrackSelectedListener {
            override fun onTrackFavoriteClicked(track: Track) {
//                GlobalScope.launch(uiContext) {
//                    if (track.isFavorite) {
//                        repository.unFavoriteTrack(track.id)
//                    } else {
//                        repository.favoriteTrack(track.id)
//                    }
//                    val updatedTrack = repository.getTrack(track.id)
//                    if (updatedTrack is Resource.Success) {
//                        tracksAdapter.data.find {
//                            it.id == track.id
//                        }?.let {
//                            val index = tracksAdapter.data.indexOf(it)
//                            tracksAdapter.data[index] = updatedTrack.data
//                            tracksAdapter.notifyItemChanged(index)
//                        }
//                    }
//                }
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
        GlobalScope.launch(uiContext) {
            //            val hotTracks = repository.getHotTracks(event.count)
//            if (hotTracks is Resource.Success) {
//                tracksAdapter.data.clear()
//                tracksAdapter.data.addAll(hotTracks.data)
//                tracksAdapter.notifyDataSetChanged()
//            }
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
//        GlobalScope.launch(uiContext) {
//            repository.refreshTracks()
//            val hotTracks = repository.getHotTracks(count)
//            if (hotTracks is Resource.Success) {
//                tracksAdapter.data.clear()
//                tracksAdapter.data.addAll(hotTracks.data)
//                tracksAdapter.notifyDataSetChanged()
//            }
//        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                HotChartsFragment()
    }
}
