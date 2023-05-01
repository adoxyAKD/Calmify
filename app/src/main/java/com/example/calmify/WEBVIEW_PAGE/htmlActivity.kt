package com.example.calmify.WEBVIEW_PAGE

//import android.annotation.SuppressLint
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.webkit.JavascriptInterface
//import android.webkit.WebView
//import android.widget.Toast
//import com.example.calmify.databinding.ActivityHtmlBinding
//import com.example.calmify.databinding.ActivityWebviewBinding

//class htmlActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityHtmlBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHtmlBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val actionbar = supportActionBar
//        actionbar?.setDisplayHomeAsUpEnabled(true)
//
//        loadPage()
//
//    }
//
//
//    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
//    fun loadPage() {
//        val browser = WebView(this)
//        browser.settings.javaScriptEnabled = true
//        browser.loadUrl("file:///android_asset/rough.html")
//        setContentView(browser)
//
//        val ws = browser.settings
//        ws.javaScriptEnabled = true
//        browser.addJavascriptInterface(object:Any() {
//            @JavascriptInterface // For API 17+
//            fun performClick(string:String) {
//                Toast.makeText(this@htmlActivity, string, Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@htmlActivity, AndroidGetData::class.java)
//                intent.putExtra("htmldata_key",string )
//                startActivity(intent)
//            }
//        }, "ok")
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
//}