package com.artushock.itcentertest.repository.retrofit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherCondition(
    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val icon: String
) : Parcelable