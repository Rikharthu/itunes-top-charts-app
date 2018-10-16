package com.rikharthu.itunestopcharts.data.source.remote.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rikharthu.itunestopcharts.data.source.remote.model.AuthorModel
import com.rikharthu.itunestopcharts.data.source.remote.model.FeedResponse
import com.rikharthu.itunestopcharts.data.source.remote.model.TrackModel
import com.rikharthu.itunestopcharts.util.*
import java.lang.reflect.Type

class FeedDeserializer : JsonDeserializer<FeedResponse> {

    // TODO do not parse images and urls here. save all of them. parsing will be done in persistence layer later

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): FeedResponse {
        val feedJson = json.asJsonObject getObject "feed"
        val tracksJson = json.asJsonObject.get("feed").asJsonObject.get("entry").asJsonArray
        val tracks = mutableListOf<TrackModel>()
        tracksJson.forEach {
            tracks.add(parseTrack(it.asJsonObject))
        }
        val authorName = feedJson getObject "author" getObject "name" getString "label"
        val authorUrl = feedJson getObject "author" getObject "uri" getString "label"
        val rights = feedJson getObject "rights" getString "label"
        val title = feedJson getObject "title" getString "label"
        val icon = feedJson getObject "icon" getString "label"
        val url = ""
        val id = feedJson getObject "id" getString "label"
        return FeedResponse(tracks, AuthorModel(authorName, authorUrl), rights, title, icon, url, id)
    }

    private fun parseTrack(trackJson: JsonObject): TrackModel {
        val name = trackJson.get("im:name").asJsonObject getString "label"
        val imagesJson = trackJson getArray "im:image"
        val images = mutableListOf<Pair<String, Int>>()
        imagesJson.forEach {
            with(it.asJsonObject) {
                images.add(Pair(getString("label"),
                        this getObject "attributes" getInt "height"))
            }
        }
        val image = images.maxBy { it.second }!!.first
        val collection = trackJson.get("im:collection").asJsonObject.get("im:name").asJsonObject getString "label"
        val price = trackJson getObject "im:price" getString "label"
        val rights = trackJson getObject "rights" getString "label"
        val title = trackJson getObject "title" getString "label"
        val id = trackJson getObject "id" getString "label"
        val artist = trackJson getObject "im:artist" getString "label"
        val releaseDate = trackJson getObject "im:releaseDate" getString "label"
        val category = trackJson getObject "category" getObject "attributes" getString "label"

        // TODO get preview Url (it has im:duration field and assetType "preview"
        val links = trackJson getArray "link"
        var link: String? = null
        links.forEach {
            with(it.asJsonObject getObject "attributes") {
                val assetType = this.get("im:assetType")?.asString
                if (assetType == "preview") {
                    link = this getString "href"
                }
            }
        }
        return TrackModel(name, image, collection, price, rights, title, id, artist, link
                ?: "", releaseDate, category)
    }
}