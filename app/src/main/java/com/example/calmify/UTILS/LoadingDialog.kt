package com.example.calmify.UTILS

import android.app.Activity
import android.app.AlertDialog
import com.example.calmify.R

class LoadingDialog( val mActivity:Activity) {

    private lateinit var isdialog:AlertDialog

    fun startLoading() {
        //set View
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item, null)

        //set Dialog
        val builder=AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)

        isdialog=builder.create()
        isdialog.show()
    }

    fun isdismiss_progressbar(){
        isdialog.dismiss()
    }
}