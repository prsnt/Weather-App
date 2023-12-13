package com.example.mvvmapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.prashant.weatherapp.data.api.ApiHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getWeatherInfo(lat: String, lon: String) =
        apiHelper.getInfo(lat, lon)

    suspend fun getForeCast(lat: String, lon: String) =
        apiHelper.getForecast(lat, lon)

}