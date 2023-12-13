package com.prashant.weatherapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.prashant.weatherapp.R.id
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.databinding.ItemCitiesBinding
import com.prashant.weatherapp.ui.dashboard.DashboardAdapter.ViewHolder


class DashboardAdapter() :
    RecyclerView.Adapter<ViewHolder>() {
    lateinit var binding1: ItemCitiesBinding
    private var list = ArrayList<AddCityModel>()
    lateinit var context: Context

    class ViewHolder(binding: ItemCitiesBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding1 = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding1)
    }


    fun setLists(list: List<AddCityModel>) {
        this.list = list as ArrayList<AddCityModel>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list?.get(position)
        binding1.model = model
        binding1.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("model",model)
            it.findNavController().navigate(id.action_navigation_dashboard_to_cityDetailFragment,bundle)

            /*(context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(id.nav_host_fragment, CityDetailFragment()).commit()*/
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }
}