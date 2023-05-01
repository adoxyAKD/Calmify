package com.example.calmify.WEBVIEW_PAGE

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.core.view.get
import com.example.calmify.R
import com.example.calmify.databinding.FragmentHtmlBinding

class HtmlFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val bind= FragmentHtmlBinding.inflate(layoutInflater)
//        val bind= FragmentHtmlBinding.inflate(R.layout.fragment_html, container, false)

        loadPage()

//        Toast.makeText(this@HtmlFragment.requireContext(), "hey", Toast.LENGTH_SHORT).show()
//        return bind.root
        return inflater.inflate(R.layout.fragment_html, container, false)
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    fun loadPage(): WebView {
        var browser = WebView(this@HtmlFragment.requireContext())
        browser.settings.javaScriptEnabled = true
        browser.loadUrl("file:///android_asset/rough.html")
//        setContentView(browser)
        return browser



//        val ws = browser.settings
//        ws.javaScriptEnabled = true
//        browser.addJavascriptInterface(object:Any() {
//            @JavascriptInterface // For API 17+
//            fun performClick(string:String) {
//                Toast.makeText(this@HtmlFragment.requireContext(), string, Toast.LENGTH_SHORT).show()
////                binding.idGetdataFromHtml.setText(string).toString()
//            }
//        }, "ok")
    }
}