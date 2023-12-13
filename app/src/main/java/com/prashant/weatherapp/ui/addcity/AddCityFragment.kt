package com.prashant.weatherapp.ui.addcity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.prashant.weatherapp.R
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.data.model.ResponseWeather
import com.prashant.weatherapp.ui.home.HomeActivity
import com.prashant.weatherapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_addcity.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCityFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    private val addCityViewModel: AddCityViewModel by viewModels()
    private var latLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_addcity, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        listners()
        button.setOnClickListener {
            if (addCityViewModel.model.lat.isNotEmpty()) {
                addCityViewModel.insertCity()

            } else
                Toast.makeText(activity, "Select the Area first", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).changeName(R.string.add_city)
        (activity as HomeActivity).addToolbar()
    }

    private fun listners() {
        addCityViewModel.responseGetInfo.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    //mMap?.clear()

                    addMarker(addCityViewModel.storeLatLng.value!!, it.data!!)
                    button.visibility = View.VISIBLE
                    //Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
        })
        addCityViewModel.responseAddCity.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "City is Added!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_dashboard)
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addMarker(latLng: LatLng, responseWeather: ResponseWeather) {
        mMap?.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(responseWeather.name)
                .snippet("Temp: " + responseWeather.main?.temp)
        )?.showInfoWindow()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera
        val ahmedabad = LatLng(23.0225, 72.5714)

        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(ahmedabad, 14.0f)
        mMap?.animateCamera(cameraUpdate)

        if (addCityViewModel.storeLatLng.value != null) {
            addMarker(
                addCityViewModel.storeLatLng.value!!,
                addCityViewModel._responseGetInfo.value?.data!!
            )
        }
        mMap?.setOnMapClickListener(GoogleMap.OnMapClickListener { latLng ->
            mMap?.clear()
            mMap?.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            this.latLng = latLng
            addCityViewModel.model.lat = latLng.latitude.toString()
            addCityViewModel.model.lng = latLng.longitude.toString()

            lifecycleScope.launch {
                addCityViewModel.callApi()
            }
        })
    }

}