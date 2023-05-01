package com.example.calmify.PRAYER_PAGE

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


//val prayerBase_URL:String ="https://drive.google.com/uc?id=1"
//const val str2:String ="appzillonappR8XFJ1WFytx0gUW0EVpWN7eC6DxYWMT2sharing"
//val str3:String = str2.replace("appzillonapp","")
//val str4:String = str3.replace("sharing","")

//val prayerEndpoint_Url:String = str4 //R8XFJ1WFytx0gUW0EVpWN7eC6DxYWMT2

val prayerBase_URL:String ="https://drive.google.com/uc?id=1/"
val str2:String ="appzillonappR8XFJ1WFytx0gUW0EVpWN7eC6DxYWMT2sharing"
//
//val splitArray1 = str2.split("appzillonapp")
//val afterAppz = splitArray1[1]
//val splitArray2 = afterAppz.split("sharing")
// val jfk = splitArray2[0]

val str3:String = str2.replace("appzillonapp","")
 val str4:String = str3.replace("sharing","").toString()

object PrayerInstance{

    val prayerInterface:PrayerInterface
    init {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        val retrofit=Retrofit.Builder()
            .baseUrl(prayerBase_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        prayerInterface=retrofit.create(PrayerInterface::class.java)
        prayerInterface.prayerInfomation(str4)
    }

    interface PrayerInterface {

        @GET()
        fun prayerInfomation(@Url url: String): Call<PrayerInfoModel>
    }
}