package com.artushock.itcentertest.repository.retrofit

import com.artushock.itcentertest.repository.retrofit.models.WeatherDetails
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    fun getSearchingWeather(
        @Query("key") key: String,
        @Query("q") q: String
    ): Single<WeatherDetails>
}

