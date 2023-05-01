package com.example.calmify.WEBVIEW_PAGE

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calmify.databinding.ActivityGetDataHtmlBinding


class getDataHtmlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetDataHtmlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataHtmlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


        getdDatafromAndroid()

        val androData = intent.getStringExtra("androdata_key1").toString()
        Toast.makeText(this@getDataHtmlActivity, "data:$androData", Toast.LENGTH_SHORT).show()




    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun getdDatafromAndroid() {
        val browser = WebView(this)
        browser.settings.javaScriptEnabled = true
        browser.loadUrl("file:///android_asset/rough2.html")
        setContentView(browser)

        /*
//        val ws = browser.settings
//        ws.javaScriptEnabled = true
//        browser.addJavascriptInterface(object:Any() {
//
//            @JavascriptInterface
//            fun showToast(toast: String) {
////                androdata_key
//
//
////                val str = "xxx"
////                browser.loadUrl("javascript:xxx('$str')")
//
//                val androData = intent.getStringExtra("androdata_key1")
//                Toast.makeText(this@getDataHtmlActivity, toast+androData.toString(), Toast.LENGTH_SHORT).show()
//
//
//
//            }
//
//        }, "Android")

         */

        val androData = intent.getStringExtra("androdata_key1").toString()
//        Toast.makeText(this@getDataHtmlActivity, "data:"+androData.toString(), Toast.LENGTH_SHORT).show()

        browser.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                //view.evaluateJavascript("loadMsg('How are you today!')", null)
                view.evaluateJavascript("loadMsg('Data: $androData ')", null)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}