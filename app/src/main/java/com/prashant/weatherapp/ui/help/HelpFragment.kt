package com.prashant.weatherapp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prashant.weatherapp.R
import com.prashant.weatherapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_help.*


class HelpFragment : Fragment() {

    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        helpViewModel =
            ViewModelProvider(this).get(HelpViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_help, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebview()
    }

    fun setupWebview() {
        webview.loadUrl("https://www.google.com");
        val webSettings: WebSettings = webview.getSettings()
        webSettings.javaScriptEnabled = true
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).changeName(R.string.title_help)
        (activity as HomeActivity).addToolbar()
    }
}