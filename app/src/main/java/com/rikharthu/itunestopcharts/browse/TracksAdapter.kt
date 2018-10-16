package com.rikharthu.itunestopcharts.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rikharthu.itunestopcharts.R
import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.util.GlideApp
import kotlinx.android.synthetic.main.item_track.view.*

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    val data = mutableListOf<Track>()

    var listener: OnTrackSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = data[position]
        with(holder.itemView) {
            trackTitle.text = track.name
            trackArtist.text = track.artist
            GlideApp.with(trackArtist)
                    .load(track.image)
                    .placeholder(R.drawable.vinyl)
                    .into(trackArt)
            setOnClickListener {
                listener?.onTrackClicked(track)
            }
            trackPosition.text = (position + 1).toString()

            favoriteIcon.setImageResource(
                    if (track.isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_favorite_border)
            favoriteIcon.setOnClickListener {
                listener?.onTrackFavoriteClicked(track)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnTrackSelectedListener {

        fun onTrackClicked(track: Track)

        fun onTrackFavoriteClicked(track: Track)
    }
}