package com.prashant.weatherapp.ui.citydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmapp.data.repository.MainRepository
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.data.model.ResponseForecast
import com.prashant.weatherapp.data.model.ResponseWeather
import com.prashant.weatherapp.util.Resource
import io.realm.Realm
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CityDetailViewModel @ViewModelInject constructor(
    var mainRepository: MainRepository,
    var realm: Realm
) : ViewModel() {
    private val _responseGetForecast = MutableLiveData<Resource<ResponseForecast>>()
    val responseGetForecast = _responseGetForecast as LiveData<Resource<ResponseForecast>>
    private val _responseGetInfo = MutableLiveData<Resource<ResponseWeather>>()
    val responseGetInfo = _responseGetInfo as LiveData<Resource<ResponseWeather>>

    fun getForecast(addCityModel: AddCityModel) {
        viewModelScope.launch {
            mainRepository.getForeCast(addCityModel.lat, addCityModel.lng).let {
                if (it.isSuccessful)
                    _responseGetForecast.postValue(Resource.success(it.body()))
                else
                    _responseGetForecast.postValue(Resource.error(it.errorBody().toString()))
            }
        }
    }

    fun getWeatherInfo(addCityModel: AddCityModel) {
        viewModelScope.launch {
            async {
                mainRepository.getWeatherInfo(addCityModel.lat, addCityModel.lng).let {
                    if (it.isSuccessful)
                        _responseGetInfo.postValue(Resource.success(it.body()))
                    else
                        _responseGetInfo.postValue(Resource.error(it.errorBody().toString()))
                }
            }.start()
            async {
                mainRepository.getForeCast(addCityModel.lat, addCityModel.lng).let {
                    if (it.isSuccessful)
                        _responseGetForecast.postValue(Resource.success(it.body()))
                    else
                        _responseGetForecast.postValue(Resource.error(it.errorBody().toString()))
                }
            }.start()
        }
    }

    fun removeBookmark(addCityModel: AddCityModel): LiveData<Boolean> {
        val _value = MutableLiveData<Boolean>()
        viewModelScope.launch {
            realm.executeTransaction {
                val result = realm.where(AddCityModel::class.java).equalTo("id", addCityModel.id)
                    .findFirst();
                result?.deleteFromRealm()
                _value.postValue(true)
            }
        }
        return _value
    }
}