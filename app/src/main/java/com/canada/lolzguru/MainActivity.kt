package com.canada.lolzguru

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.canada.lolzguru.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val MAIN_PAGE = "https://lolz.guru"
    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSplash()
        initBottomNav()
        initWebView()
    }

    private fun initSplash() {
        if (isFirstLaunch)
        binding.splashView.root.visibility = View.VISIBLE
    }

    private fun initBottomNav() = with(binding) {
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    webView.loadUrl(MAIN_PAGE)
                }
                R.id.navigation_back -> {
                    webView.goBack()
                }
                R.id.navigation_forward -> {
                    webView.goForward()
                }
                R.id.navigation_reload -> {
                    webView.reload()
                }
                R.id.navigation_share -> {
                    Util.shareUrl(this@MainActivity)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() = with(binding.webView) {
        webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                AppPreferences.lastLoadedPageURL = request?.url.toString()
                return Util.chekOverLoad(view!!, request?.url.toString())
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.loadProgress.isVisible = true
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.loadProgress.isVisible = false
                isFirstLaunch = false
                binding.splashView.root.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        loadUrl(MAIN_PAGE)
    }
}