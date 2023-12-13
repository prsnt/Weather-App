package com.prashant.weatherapp.data.api

import com.prashant.weatherapp.data.model.ResponseForecast
import com.prashant.weatherapp.data.model.ResponseWeather
import com.prashant.weatherapp.util.CommonUtils.Companion.appidAPI
import com.prashant.weatherapp.util.CommonUtils.Companion.appidParam
import com.prashant.weatherapp.util.CommonUtils.Companion.forecastAPI
import com.prashant.weatherapp.util.CommonUtils.Companion.latParam
import com.prashant.weatherapp.util.CommonUtils.Companion.lonParam
import com.prashant.weatherapp.util.CommonUtils.Companion.unitsAPI
import com.prashant.weatherapp.util.CommonUtils.Companion.unitsParam
import com.prashant.weatherapp.util.CommonUtils.Companion.weatherAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiInterface {

    @GET(weatherAPI)
    suspend fun getInfo(
        @Query(latParam) lat: String,
        @Query(lonParam) lon: String,
        @Query(unitsParam) unit: String = unitsAPI,
        @Query(appidParam) appid: String = appidAPI,
    ): Response<ResponseWeather>

    @GET(forecastAPI)
    suspend fun getForecast(
        @Query(latParam) lat: String,
        @Query(lonParam) lon: String,
        @Query(unitsParam) unit: String = unitsAPI,
        @Query(appidParam) appid: String = appidAPI,
    ): Response<ResponseForecast>

}