package com.example.calmify.LOCK_APPS_PAGE

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.calmify.R

class AppListAdapter(private val appList:List<AppListModelItem>): RecyclerView.Adapter<AppListAdapter.myAppListViewHolder>() {
//class AppListAdapter(val context: Context, val appList:AppListModel): RecyclerView.Adapter<AppListAdapter.myAppListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAppListViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.app_item,parent,false)
        return myAppListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myAppListViewHolder, position: Int) {

        val currentAppItem=appList[position]
        holder.appName.text= currentAppItem.appPakgName
        holder.appIcon.setImageDrawable(currentAppItem.appIconimg)

//        Glide.with(context)
//            .load(prayerImage_URL)
//            .placeholder(R.drawable.img2)
//            .into(holder.prayerImg)


    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class myAppListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val appName= itemView.findViewById<TextView>(R.id.tvAppName)
        val appIcon= itemView.findViewById<ImageView>(R.id.id_AppIcon)

    }
}