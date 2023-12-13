package com.prashant.weatherapp.ui.citydetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.prashant.weatherapp.R
import com.prashant.weatherapp.data.AddCityModel
import com.prashant.weatherapp.data.model.ListItem
import com.prashant.weatherapp.databinding.CityDetailBinding
import com.prashant.weatherapp.ui.dashboard.DashboardViewModel
import com.prashant.weatherapp.ui.home.HomeActivity
import com.prashant.weatherapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.city_detail.*


@AndroidEntryPoint
class CityDetailFragment : Fragment() {
    private val viewmodel: CityDetailViewModel by viewModels()
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var binding: CityDetailBinding
    lateinit var addCityModel: AddCityModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.city_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewmodel
        getintentData()

        binding.toolbar.setTitle(addCityModel.name)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        listners()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).changeName(addCityModel.name)
        (activity as HomeActivity).addBackButton()
        (activity as HomeActivity).removeToolbar()
    }

    private fun getintentData() {
        if (requireArguments().containsKey("model"))
            addCityModel = requireArguments().getSerializable("model") as AddCityModel
        viewmodel.getWeatherInfo(addCityModel)
        binding.citymodel = addCityModel
    }

    private fun listners() {
        val adapter = CityDetailAdapter()
        //val adapter2 = CityDetailAdapter()
        binding.adapter = adapter
        //binding.adapter2 = adapter2
        viewmodel.responseGetForecast.observe(viewLifecycleOwner, Observer {
            Log.d("PRT", "listners: " + it.data.toString())
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {

                    it.data?.let { it1 ->
                        adapter.setLists(it1.list as List<ListItem>)
                        adapter.filter.filter("1")
                        binding.progressBar2.visibility = View.GONE
                    }
                }
                else -> {
                    binding.progressBar2.visibility = View.GONE
                }
            }

        })

        viewmodel.responseGetInfo.observe(viewLifecycleOwner, Observer {
            Log.d("PRT", "listners: " + it.data.toString())
            binding.model = it.data
            Glide.with(requireActivity())
                .load("http://openweathermap.org/img/w/" + it.data?.weather?.get(0)?.icon + ".png")
                .centerCrop() // scale to fit entire image within ImageView
                .into(binding.imgWeather);
        })
        binding.fab.setOnClickListener {
            viewmodel.removeBookmark(addCityModel).observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.navigation_dashboard)
            })
        }
    }
}