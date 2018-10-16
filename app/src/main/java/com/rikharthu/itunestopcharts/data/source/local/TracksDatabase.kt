package com.rikharthu.itunestopcharts.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rikharthu.itunestopcharts.data.source.local.model.TrackEntity

@Database(entities = [TrackEntity::class], version = 1)
abstract class TracksDatabase : RoomDatabase() {

    abstract fun tracksDao(): TracksDao
}