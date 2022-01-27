package com.example.nyarticles.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nyarticles.R
import com.example.nyarticles.model.Results
import com.example.nyarticles.network.interfaces.ItemListener
import java.lang.Exception
import java.util.*

class MainAdapter(
    private val context: Context,
    private val listResultData: ArrayList<Results>,
    val itemListener: ItemListener
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    val TAG = "MainAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layoyt_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list2 = listResultData[position]

        holder.item_title.text = list2.title
        holder.item_date.text = list2.published_date

        try {
            Glide.with(context)
                .load(list2.media?.get(0)?.media_metadata?.get(0)?.url)
                .placeholder(R.drawable.pla)
                .into(holder.item_icon)
        }catch (e:Exception){
            Log.e(TAG,e.message.toString())
            Log.e(TAG,e.cause.toString())
        }

        holder.itemView.setOnClickListener {
            itemListener.onItemClick(position)
        }


        //Logger.LogE(TAG, "data :" + permissionDetailList!![position].getDescription())
    }

    override fun getItemCount(): Int {
        return listResultData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_icon: ImageView
        var item_title: TextView
        var item_date: TextView

        init {
            item_icon = itemView.findViewById<View>(R.id.imageview) as ImageView
            item_title = itemView.findViewById<View>(R.id.title) as TextView
            item_date = itemView.findViewById<View>(R.id.date) as TextView
        }
    }
}