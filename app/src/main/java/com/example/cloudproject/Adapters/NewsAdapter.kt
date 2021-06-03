package com.example.cloudproject.Adapters

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
import com.example.cloudproject.Fragments.NewsDataFragment
import com.example.cloudproject.Models.DataModel
import com.example.cloudproject.Models.NewsModel
import com.example.cloudproject.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso


class NewsAdapter(options: FirestoreRecyclerOptions<NewsModel>) :
    FirestoreRecyclerAdapter<NewsModel, NewsAdapter.NewsAdapterVH>(options) {
    var fragmentManager: FragmentManager? = null

    protected var context: Context? = null

    fun Example(context: Context, fragmentManager: FragmentManager) {
        this.context = context.applicationContext
        this.fragmentManager = fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterVH {
        return NewsAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.newsitem, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsAdapterVH, postion: Int, model: NewsModel) {
        holder.tittleNews.text = model.tittleNews


        if (model.imageUrlNews.isEmpty()) {
            Picasso.get()
                .load(R.drawable.ic_baseline_image_24)
                .into(holder.img);

        } else {

            Picasso.get()
                .load(model.imageUrlNews)
                .into(holder.img)


        }
        val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager

        holder.itemView.setOnClickListener() {

            val snapshot = snapshots.getSnapshot(holder.adapterPosition)
            snapshot.id

            val fragment = NewsDataFragment()
            val bundle3 = Bundle()
            bundle3.putString("tittleNews", model.tittleNews);
            bundle3.putString("topicNews", model.topicNews);
            bundle3.putString("sourceNews", model.sourceNews);
            bundle3.putString("docid", snapshot.id);

            if (model.imageUrlNews != null) {

                bundle3.putString("imageUrlNews", model.imageUrlNews);

            }
            fragment.arguments = bundle3;
            manager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()

            }

        }

    }


    inner class NewsAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tittleNews: TextView = itemView.findViewById<TextView>(R.id.item_title2)
        var img: ImageView = itemView.findViewById(R.id.item_image2)


    }


}