package com.rikharthu.itunestopcharts.data.source.remote

import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.remote.model.TrackModel

fun TrackModel.asTrack(): Track {
    return Track(this.name, this.image, this.collection, this.price, this.rights,
            this.title, this.id, this.artist, this.previewUrl, this.releaseDate,
            this.category, true, false) // track from remote is always hot and not favorite
}

fun Track.asModel(): TrackModel {
    return TrackModel(this.name, this.image, this.collection, this.price, this.rights,
            this.title, this.id, this.artist, this.previewUrl, this.releaseDate, this.category)
}