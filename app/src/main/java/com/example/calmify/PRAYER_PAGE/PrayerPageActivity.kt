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

import com.example.calmify.databinding.ActivityMainBinding
import com.example.calmify.databinding.ActivityPrayerPageBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback

//import retrofit2.Response

class PrayerPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrayerPageBinding
    var prayerInfoItem = arrayOf<PrayerInfoModelItem>()
    val prayerInfo = PrayerInfoModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        var str1 = "https://drive.google.com/uc?id=1"
        var str2 = "appzillonappR8XFJ1WFytx0gUW0EVpWN7eC6DxYWMT2sharing"
        var str3 = str2.replace("appzillonapp", "")
        val prayerURL = str1 + str3.replace("sharing", "")
/*
//        val apiInterface = ApiInterface.create().getMovies()
//        val prayer1=PrayerInstance.prayerInterface.prayerInfomation()
        val prayer1=PrayerInstance.prayerInterface.prayerInfomation()

        prayer1.enqueue(object:Callback<PrayerInfoModel>{
            override fun onResponse(
                call: Call<PrayerInfoModel>,
                response: retrofit2.Response<PrayerInfoModel>
            ) {
                val prayerInfo:PrayerInfoModel?=response.body()
                if (prayerInfo!=null){
                    val adapter= myPrayerAdapter(this@PrayerPageActivity,prayerInfo)
                    binding.idMyPrayerRecycler.layoutManager=LinearLayoutManager(this@PrayerPageActivity)
                    binding.idMyPrayerRecycler.adapter=adapter
                }
            }

            override fun onFailure(call: Call<PrayerInfoModel>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })



 */

        //volly:
        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //
        val stringRequest = StringRequest( Request.Method.GET,prayerURL,
            Response.Listener {
                val gson = GsonBuilder().create()
                prayerInfoItem = gson.fromJson(it, Array<PrayerInfoModelItem>::class.java)
                prayerInfoItem.forEach {
                    prayerInfo.add(it)
                    loading.isdismiss_progressbar() // for dissmissing progressbar
                }

                val adapter = myPrayerAdapter(this, prayerInfo)
                binding.idMyPrayerRecycler.layoutManager = LinearLayoutManager(this)
                binding.idMyPrayerRecycler.adapter = adapter
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                loading.isdismiss_progressbar()
            })

        val vollyQueue = Volley.newRequestQueue(this)
        vollyQueue.add(stringRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

