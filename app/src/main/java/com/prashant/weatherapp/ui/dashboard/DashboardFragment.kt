package com.prashant.weatherapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.prashant.weatherapp.R
import com.prashant.weatherapp.databinding.FragmentDashboardBinding
import com.prashant.weatherapp.ui.home.HomeActivity
import com.prashant.weatherapp.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_dashboard,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = dashboardViewModel
        binding.recyclerview.addItemDecoration(DividerItemDecoration(activity, HORIZONTAL))
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                getCities()
            }

        getCities()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).changeName(R.string.title_dashboard)
        (activity as HomeActivity).removeBackButton()
        (activity as HomeActivity).addToolbar()
    }

    fun getCities() {
        val adapter = DashboardAdapter()
        binding.adapter = adapter
        // Observe data from viewModel
        dashboardViewModel.responseCities.observe(viewLifecycleOwner, {
            Log.e("MainActivity", it.toString())
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvNoDataFound.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data.let { it1 ->
                        adapter.setLists(it1!!)
                        if (it1.isNotEmpty())
                            binding.tvNoDataFound.visibility = View.GONE
                        else
                            binding.tvNoDataFound.visibility = View.VISIBLE
                    }
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvNoDataFound.visibility = View.VISIBLE
                }
            }

        })
    }
}