package com.artushock.itcentertest.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artushock.itcentertest.repository.room.entities.WeatherEntity
import io.reactivex.rxjava3.core.Flowable

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE `query` = :q LIMIT 1")
    fun selectByWeatherByQuery(q: String): Flowable<List<WeatherEntity>>

    @Query("SELECT * FROM weather WHERE `query` LIKE '%' || :q || '%'")
    fun selectByPartOfQuery(q: String): Flowable<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherEntity)
}