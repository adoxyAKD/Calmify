package com.example.calmify.WEBVIEW_PAGE

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.example.calmify.R
import com.example.calmify.databinding.ActivityAndroidGetDataBinding
import com.example.calmify.databinding.ActivityWebviewBinding

class AndroidGetData : AppCompatActivity() {

    private lateinit var binding: ActivityAndroidGetDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAndroidGetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val htmldata = intent.getStringExtra("htmldata_key")
        binding.idGetDatafromHtml.setText("Data from html webview:\n "+htmldata).toString()

        binding.idSendToHtmlButton.setOnClickListener{
            sendDatatoHtml()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun sendDatatoHtml() {
        val intent = Intent(this@AndroidGetData, getDataHtmlActivity::class.java)

        val data =binding.idAndroidDataTxvw.text
        intent.putExtra("androdata_key1",data )
//        Toast.makeText(this@AndroidGetData, "data: "+data, Toast.LENGTH_SHORT).show()
        startActivity(intent)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}