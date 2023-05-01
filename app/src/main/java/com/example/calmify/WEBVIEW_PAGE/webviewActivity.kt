package com.example.calmify.WEBVIEW_PAGE

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.calmify.PRAYER_PAGE.ppathActivity
import com.example.calmify.R
import com.example.calmify.databinding.ActivityMainBinding
import com.example.calmify.databinding.ActivityWebviewBinding
import com.facebook.appevents.ml.Utils
import com.google.android.gms.common.util.IOUtils
import com.google.android.material.tabs.TabLayout

class webviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

//        loadPage()
        setUpAdapter()
    }


    //for viewPager adapter
    private fun setUpAdapter() {
        val adapter_tab= TabAdapter(this,binding.idTablayout.tabCount)
        binding.idViewPager.adapter=adapter_tab

        binding.idViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
               binding.idTablayout.selectTab(binding.idTablayout.getTabAt(position))
//                loadPage()

            }
        })

        binding.idTablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.idViewPager.currentItem=tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
    }





    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    fun loadPage() {
        val browser = WebView(this)
        browser.settings.javaScriptEnabled = true
        browser.loadUrl("file:///android_asset/rough.html")
        setContentView(browser)

        val ws = browser.settings
        ws.javaScriptEnabled = true
        browser.addJavascriptInterface(object:Any() {
            @JavascriptInterface // For API 17+
            fun performClick(string:String) {
                Toast.makeText(this@webviewActivity, string, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@webviewActivity, AndroidGetData::class.java)
                intent.putExtra("htmldata_key",string )
                startActivity(intent)
            }
        }, "ok")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}