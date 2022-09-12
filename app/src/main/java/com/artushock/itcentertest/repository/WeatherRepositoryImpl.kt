package com.artushock.itcentertest.repository

import com.artushock.itcentertest.BuildConfig
import com.artushock.itcentertest.repository.retrofit.WeatherService
import com.artushock.itcentertest.repository.retrofit.models.WeatherDetails
import com.artushock.itcentertest.repository.room.WeatherQueriesDataBase
import com.artushock.itcentertest.repository.room.entities.WeatherEntity
import com.artushock.itcentertest.ui.models.Weather
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherService,
    private val db: WeatherQueriesDataBase
) : WeatherRepository {

    override fun searchWeatherByQuery(query: String): Single<WeatherDetails> {
        val key = BuildConfig.WEATHER_API_KEY
        return api.getSearchingWeather(key, query)
    }

    override fun getPreviousQueries(queryPart: String): Flowable<List<WeatherEntity>> {
        return db.weatherDao().selectByPartOfQuery(queryPart)
    }

    override fun addWeatherToDB(query: String, weather: Weather) {
        Single.fromCallable {
            val entity = WeatherEntity(
                id = query.hashCode(),
                query = query,
                place = weather.place,
                latitude = weather.latitude,
                longitude = weather.longitude,
                temp = weather.temp,
                humidity = weather.humidity,
                pressure = weather.pressure,
                icon = weather.icon
            )
            db.weatherDao().insertWeather(
                entity
            )
            entity
        }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getWeatherFromDB(query: String): Flowable<List<WeatherEntity>> {
        return db.weatherDao().selectByWeatherByQuery(query)
    }
}
