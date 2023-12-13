package com.prashant.weatherapp.ui.addcity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmapp.data.repository.MainRepository
import com.google.android.gms.maps.model.LatLng
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.data.model.ResponseWeather
import com.prashant.weatherapp.util.Resource
import io.realm.Realm

class AddCityViewModel @ViewModelInject constructor(
    val realm: Realm,
    var mainRepository: MainRepository
) : ViewModel() {

    val _responseAddCity = MutableLiveData<Resource<String>>()
    val responseAddCity = _responseAddCity as LiveData<Resource<String>>
    val _responseGetInfo = MutableLiveData<Resource<ResponseWeather>>()
    val responseGetInfo = _responseGetInfo as LiveData<Resource<ResponseWeather>>
    val storeLatLng = MutableLiveData<LatLng>()
    var model = AddCityModel()

    fun insertCity() {
        realm.executeTransaction {
            realm.insert(model)
            _responseAddCity.postValue(Resource.success("Added Successfully"))
            model = AddCityModel()
        }
    }

    suspend fun callApi() {
        storeLatLng.value = LatLng(model.lat.toDouble(), model.lng.toDouble())
        mainRepository.getWeatherInfo(model.lat, model.lng).let {
            if (it.isSuccessful) {
                model.name = it.body()?.name!!
                _responseGetInfo.postValue(Resource.success(it.body()))
            } else
                _responseGetInfo.postValue(Resource.error(it.errorBody().toString()))
        }
    }
}