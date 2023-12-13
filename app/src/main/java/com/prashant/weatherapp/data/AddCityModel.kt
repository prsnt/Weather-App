package com.prashant.weatherapp.data

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.google.android.gms.maps.model.LatLng
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.io.Serializable
import java.util.*


open class AddCityModel(
    _lat: String = "",
    _lng: String = "",
    _name: String = "",
) : RealmObject(), Serializable {

    @PrimaryKey
    var id: ObjectId = ObjectId()
    var lat: String = _lat
    var lng: String = _lng
    var name: String = _name

}