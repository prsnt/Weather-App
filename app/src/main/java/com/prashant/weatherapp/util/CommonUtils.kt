package com.prashant.weatherapp.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.prashant.weatherapp.data.AddCityModel
import java.text.SimpleDateFormat
import java.util.*


class CommonUtils {
    companion object {

        //API params
        const val forecastAPI = "forecast"
        const val weatherAPI = "weather"
        const val unitsAPI = "metric"
        const val appidAPI = "fae7190d7e6433ec3a45285ffcf55c86"
        const val latParam = "lat"
        const val lonParam = "lon"
        const val unitsParam = "units"
        const val appidParam = "appid"

        @JvmStatic
        @BindingAdapter("setAdapter")
        fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
            this.run {
                this.setHasFixedSize(true)
                this.adapter = adapter
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["setupVisibility"])
        fun ProgressBar.progressVisibility(isVisible: Boolean) {
            if (isVisible)
                this.visibility = View.VISIBLE
            else
                this.visibility = View.GONE

        }

        @JvmStatic
        @BindingAdapter(value = ["noDataFoundVisibility"])
        fun setvisibility(view: View, resource: Resource<List<AddCityModel>>?) {
            resource.let {
                if (it?.status == Status.LOADING) {
                    view.visibility = View.GONE
                } else if (it?.status == Status.SUCCESS && it.data?.size!! > 0) {
                    view.visibility = View.GONE
                } else if (it?.status == Status.SUCCESS && it.data?.size!! == 0) {
                    view.visibility = View.VISIBLE
                } else if (it?.status == Status.ERROR) {
                    view.visibility = View.VISIBLE
                }
            }
        }

        @JvmStatic
        fun isCurrentDate(my_date: String): Boolean {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val strDate: Date = changeDateFormat(sdf.parse(my_date))!!
            return changeDateFormat(Date())?.equals(strDate)!!
        }

        @JvmStatic
        fun changeDatetoTime(my_date: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdf1 = SimpleDateFormat("HH:mm")
            return sdf1.format(sdf.parse(my_date))
        }

        fun changeDateFormat(date: Date): Date? {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.parse(sdf.format(date))
        }
    }
}