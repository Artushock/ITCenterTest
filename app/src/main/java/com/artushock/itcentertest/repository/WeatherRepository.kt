package com.artushock.itcentertest.repository

import com.artushock.itcentertest.repository.retrofit.models.WeatherDetails
import com.artushock.itcentertest.repository.room.entities.WeatherEntity
import com.artushock.itcentertest.ui.models.Weather
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun searchWeatherByQuery(query: String): Single<WeatherDetails>
    fun addWeatherToDB(query: String, weather: Weather)
    fun getWeatherFromDB(query: String): Flowable<List<WeatherEntity>>
    fun getPreviousQueries(queryPart: String): Flowable<List<WeatherEntity>>
}