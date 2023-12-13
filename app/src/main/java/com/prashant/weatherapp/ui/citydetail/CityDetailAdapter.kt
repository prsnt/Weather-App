package com.prashant.weatherapp.ui.citydetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.prashant.weatherapp.data.model.ListItem
import com.prashant.weatherapp.databinding.ItemForecastBinding
import com.prashant.weatherapp.util.CommonUtils

class CityDetailAdapter :
    RecyclerView.Adapter<CityDetailAdapter.ViewHolder>(), Filterable {
    lateinit var binding1: ItemForecastBinding
    var list = ArrayList<ListItem>()
    var listFilter = ArrayList<ListItem>()
    private var fRecords: Filter? = null

    class ViewHolder(binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding1 = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding1)
    }

    fun setLists(list: List<ListItem>) {
        this.list = list as ArrayList<ListItem>
        this.listFilter = this.list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = listFilter.get(holder.adapterPosition)
        binding1.model = model
        binding1.tvTime.setText("Time: " + CommonUtils.changeDatetoTime(model.dtTxt!!))
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    override fun getFilter(): Filter {

        return myFilter
    }

    private val myFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ListItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(list)
            } else {
                for (item in list) {
                    if (CommonUtils.isCurrentDate(item.dtTxt!!))
                        filteredList.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listFilter.clear()
            if (results.values != null)
                listFilter.addAll(results.values as List<ListItem>)
            notifyDataSetChanged()
        }
    }
}