package com.prashant.weatherapp.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.util.Resource
import io.realm.Realm
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(val realm: Realm) : ViewModel() {

    val _responseCities = MutableLiveData<Resource<List<AddCityModel>>>()
    val responseCities = _responseCities as LiveData<Resource<List<AddCityModel>>>


    init {
        getCities()
    }

    fun getCities() {
        viewModelScope.launch {
            realm.executeTransaction {
                _responseCities.postValue(
                    Resource.success(
                        realm.copyFromRealm(realm.where(AddCityModel::class.java).findAll())
                    )
                )
            }
        }
    }
}