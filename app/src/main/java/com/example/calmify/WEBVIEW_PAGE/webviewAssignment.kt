package com.example.calmify.WEBVIEW_PAGE

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.calmify.R

class webviewAssignment : AppCompatActivity() {

    lateinit var mEditText : EditText
    lateinit var mButtonSend : Button

    lateinit var webViewSample : WebView

    private val mFilePath = "file:///android_asset/sampleweb.html"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_assignment)

        mEditText = findViewById(R.id.editInput)
        webViewSample = findViewById(R.id.webViewSample)

        webViewSample.settings.javaScriptEnabled = true
        webViewSample.addJavascriptInterface(JSBridge(this,mEditText),"JSBridge")
        webViewSample.loadUrl(mFilePath)

        mButtonSend = findViewById(R.id.sendData)
        mButtonSend.setOnClickListener {
            sendDataToWebView()
        }
    }

    //* Receive message from webview and pass on to native.
    class JSBridge(val context: Context, val editTextInput: EditText){
        @JavascriptInterface
        fun showMessageInNative(message:String){
//            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            editTextInput.setText(message)
        }
    }

    //* Send data to webview through function updateFromNative.
    private fun sendDataToWebView(){
         webViewSample.evaluateJavascript(
            "javascript: " +"updateFromNative(\"" + mEditText.text + "\")",
            null)
    }
}