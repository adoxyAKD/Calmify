package com.example.calmify.PRAYER_PAGE

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.calmify.R
import com.example.calmify.UTILS.LoadingDialog
import com.example.calmify.databinding.ActivityPpathBinding
import com.example.calmify.databinding.ActivityPrayerPageBinding
import com.google.gson.GsonBuilder

class ppathActivity : AppCompatActivity() {

    lateinit var binding: ActivityPpathBinding
    var ppathInfoItem = arrayOf<ppathmodelItem>()
    val ppathInfo =ppathModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPpathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


        var str1 = "https://drive.google.com/uc?id=1"
        val p_path = intent.getStringExtra("ppath_key") //getting from  ppath Adapter

        var str3 = p_path?.replace("appzillonapp", "")
        val ppathURL_modified = str1 + (str3?.replace("sharing", ""))


        //volly:
        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //
        val stringRequest1 = StringRequest( Request.Method.GET, ppathURL_modified,
            Response.Listener {
                val gson = GsonBuilder().create()
                ppathInfoItem = gson.fromJson(it, Array<ppathmodelItem>::class.java)
                ppathInfoItem.forEach {
                    ppathInfo.add(it)
                    loading.isdismiss_progressbar()
                }

                val adapter = ppathAdapter(this, ppathInfo)
                binding.idMyPrayerRecycler.layoutManager = LinearLayoutManager(this)
                binding.idMyPrayerRecycler.adapter = adapter
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
//                Toast.makeText(applicationContext, "some error", Toast.LENGTH_SHORT).show()
                loading.isdismiss_progressbar()
            })

        val vollyQueue1 = Volley.newRequestQueue(this)
        vollyQueue1.add(stringRequest1)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}