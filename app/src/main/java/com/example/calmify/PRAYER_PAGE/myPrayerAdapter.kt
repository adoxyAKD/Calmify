package com.example.calmify.PRAYER_PAGE

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.calmify.R


class myPrayerAdapter(val context: Context, val prayerInfoModel_list:PrayerInfoModel): RecyclerView.Adapter<myPrayerAdapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.prayer_item,parent,false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        holder.prayerCategory.text= prayerInfoModel_list.get(position).categories
        holder.prayerCount.text= prayerInfoModel_list.get(position).count.toString()

        var str1 = "https://drive.google.com/uc?id=1"
        var str2 = prayerInfoModel_list.get(position).image
        var str3 = str2.replace("appzillonapp", "")
        val prayerImage_URL = str1 + str3.replace("sharing", "")

        Glide.with(context)
            .load(prayerImage_URL)
            .placeholder(R.drawable.img2)
            .into(holder.prayerImg)


        holder.itemView.setOnClickListener{
//            Toast.makeText(context, "pos "+position, Toast.LENGTH_SHORT).show()
            var ppath = prayerInfoModel_list.get(position).p_path

            val intent = Intent(holder.itemView.context, ppathActivity::class.java)
            intent.putExtra("ppath_key",ppath )
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return prayerInfoModel_list.size
    }

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val prayerImg= itemView.findViewById<ImageView>(R.id.id_prayerImg)
        val prayerCategory= itemView.findViewById<TextView>(R.id.id_prayerCategory)
        val prayerCount= itemView.findViewById<TextView>(R.id.id_prayerCount)
    }
}