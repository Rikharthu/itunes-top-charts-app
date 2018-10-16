package com.rikharthu.itunestopcharts.data.source.local

import com.rikharthu.itunestopcharts.data.Track
import com.rikharthu.itunestopcharts.data.source.local.model.TrackEntity

fun TrackEntity.asTrack(): Track {
    return Track(this.name, this.image, this.collection, this.price, this.rights,
            this.title, this.id, this.artist, this.previewUrl, this.releaseDate,
            this.category, this.isHot, this.isFavorite)
}

fun Track.asEntity(): TrackEntity {
    return TrackEntity(this.id, this.name, this.image, this.collection, this.price, this.rights,
            this.title, this.artist, this.previewUrl, this.releaseDate, this.category,
            this.isHot, this.isFavorite)
}