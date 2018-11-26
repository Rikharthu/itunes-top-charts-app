package com.rikharthu.itunestopcharts.data.source.local

import androidx.room.*
import com.rikharthu.itunestopcharts.data.source.local.model.CachedTrack

@Dao
interface TracksDao {

    @Query("SELECT * FROM hotTracks")
    fun getTracks(): List<CachedTrack>

    @Query("SELECT * FROM hotTracks LIMIT :count")
    fun getTracks(count: Int): List<CachedTrack>

    @Query("SELECT * FROM hotTracks WHERE isHot = 1 LIMIT :count")
    fun getHotTracks(count: Int): List<CachedTrack>

    @Query("SELECT * FROM hotTracks WHERE isFavorite = 1")
    fun getFavoriteTracks(): List<CachedTrack>

    @Query("SELECT * FROM hotTracks WHERE id = :trackId")
    fun getTrackById(trackId: String): CachedTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTracks(vararg tracks: CachedTrack)

    @Update
    fun updateTrack(track: CachedTrack): Int

    @Query("DELETE FROM hotTracks WHERE id = :trackId")
    fun deleteTrackById(trackId: String)

    @Query("DELETE FROM hotTracks")
    fun deleteAllTracks(): Int

    @Query("UPDATE hotTracks SET isHot = :isHot WHERE id = :trackId")
    fun setHotStatus(isHot: Boolean, trackId: String)

    @Query("UPDATE hotTracks SET isHot = 0")
    fun setNotHotTracks(): Int

    @Query("UPDATE hotTracks SET isFavorite = :isFavorite WHERE id = :trackId")
    fun setFavoriteStatus(isFavorite: Boolean, trackId: String)

    @Query("DELETE FROM hotTracks WHERE isFavorite = 0")
    fun deleteNotFavoriteTracks(): Int
}