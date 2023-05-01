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

class ppathAdapter(val context: Context, val ppathModel_list:ppathModel): RecyclerView.Adapter<ppathAdapter.myppathViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myppathViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.prayerpath_item, parent, false)
        return myppathViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myppathViewHolder, position: Int) {

        holder.ppathCategory.text = ppathModel_list.get(position).title
        holder.ppathDescription.text = ppathModel_list.get(position).pray

        holder.itemView.setOnClickListener{
//            Toast.makeText(context, "pos"+position, Toast.LENGTH_SHORT).show()


            var ppath_ttl = ppathModel_list.get(position).title
            var ppath_desc= ppathModel_list.get(position).pray

            val intent = Intent(holder.itemView.context, ppathShowdetailsActivity::class.java)
            intent.putExtra("ppath_key_title",ppath_ttl )
            intent.putExtra("ppath_key_desc",ppath_desc )
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return ppathModel_list.size
    }


    inner class myppathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ppathCategory = itemView.findViewById<TextView>(R.id.id_ppathCategory)
        val ppathDescription = itemView.findViewById<TextView>(R.id.id_ppathDescription)

    }
}
