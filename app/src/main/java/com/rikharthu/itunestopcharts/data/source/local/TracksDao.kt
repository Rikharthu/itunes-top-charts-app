package com.rikharthu.itunestopcharts.data.source.local

import androidx.room.*
import com.rikharthu.itunestopcharts.data.source.local.model.TrackEntity

@Dao
interface TracksDao {

    @Query("SELECT * FROM tracks")
    fun getTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks LIMIT :count")
    fun getTracks(count: Int): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE isHot = 1 LIMIT :count")
    fun getHotTracks(count: Int): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE isFavorite = 1")
    fun getFavoriteTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE id = :trackId")
    fun getTrackById(trackId: String): TrackEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTracks(vararg tracks: TrackEntity)

    @Update
    fun updateTrack(track: TrackEntity): Int

    @Query("DELETE FROM tracks WHERE id = :trackId")
    fun deleteTrackById(trackId: String)

    @Query("DELETE FROM tracks")
    fun deleteAllTracks(): Int

    @Query("UPDATE tracks SET isHot = :isHot WHERE id = :trackId")
    fun setHotStatus(isHot: Boolean, trackId: String)

    @Query("UPDATE tracks SET isHot = 0")
    fun setNotHotTracks(): Int

    @Query("UPDATE tracks SET isFavorite = :isFavorite WHERE id = :trackId")
    fun setFavoriteStatus(isFavorite: Boolean, trackId: String)

    @Query("DELETE FROM tracks WHERE isFavorite = 0")
    fun deleteNotFavoriteTracks(): Int
}