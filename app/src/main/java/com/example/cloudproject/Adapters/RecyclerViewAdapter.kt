package com.example.cloudproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.Fragments.DataFragment
import com.example.cloudproject.Models.DataModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso


class DataAdapter(options: FirestoreRecyclerOptions<DataModel>) :
    FirestoreRecyclerAdapter<DataModel, DataAdapter.DataAdapterVH>(options) {
    var fragmentManager: FragmentManager? = null

    protected var context: Context? = null

    fun Example(context: Context, fragmentManager: FragmentManager) {
        this.context = context.applicationContext
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterVH {
        return DataAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.mainitem, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataAdapterVH, postion: Int, model: DataModel) {
        holder.tittle.text = model.tittle
        holder.topic.text = model.topic

        if (model.imageUrl.isEmpty()) {
            Picasso.get()
                .load(R.drawable.ic_baseline_image_24)
                .into(holder.img);

        } else {

            Picasso.get()
                .load(model.imageUrl)
                .into(holder.img)


        }
        val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager

        holder.itemView.setOnClickListener() {


            val fragment = DataFragment()
            val bundle = Bundle()
            bundle.putString("tittle", model.tittle);
            bundle.putString("topic", model.topic);

            if (model.imageUrl != null) {

                bundle.putString("imageUrl", model.imageUrl);

            }
            fragment.arguments = bundle;
            manager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()

            }

        }

    }


    inner class DataAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tittle: TextView = itemView.findViewById<TextView>(R.id.item_title)
        var img: ImageView = itemView.findViewById(R.id.item_image)
        var topic: TextView = itemView.findViewById(R.id.item_topic)


    }


}