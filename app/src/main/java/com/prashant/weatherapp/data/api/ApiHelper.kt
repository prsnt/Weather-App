package com.prashant.weatherapp.data.api

import com.prashant.weatherapp.data.model.ResponseForecast
import com.prashant.weatherapp.data.model.ResponseWeather
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiHelper {
    suspend fun getInfo(lat: String, lon: String): Response<ResponseWeather>
    suspend fun getForecast(lat: String, lon: String): Response<ResponseForecast>
}