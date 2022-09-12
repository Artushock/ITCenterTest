package com.artushock.itcentertest.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artushock.itcentertest.repository.room.entities.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherQueriesDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}