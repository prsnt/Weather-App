package com.prashant.weatherapp.data.api

import com.prashant.weatherapp.data.api.ApiHelper
import com.prashant.weatherapp.data.api.ApiInterface
import com.prashant.weatherapp.data.model.ResponseForecast
import com.prashant.weatherapp.data.model.ResponseWeather
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiInterface: ApiInterface) : ApiHelper {

    override suspend fun getInfo(lat: String, lon: String): Response<ResponseWeather> {
        return apiInterface.getInfo(lat, lon)
    }

    override suspend fun getForecast(lat: String, lon: String): Response<ResponseForecast> {
        return apiInterface.getForecast(lat, lon)
    }
}