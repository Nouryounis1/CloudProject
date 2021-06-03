package com.example.cloudproject.Fragments

import android.media.Image
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginEnd
import com.example.cloudproject.R
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var imgvNews: ImageView
lateinit var test_tittleNews: TextView
lateinit var test_topicNews: TextView
lateinit var test_sourceNews: TextView

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_news_data, container, false)
        imgvNews = root.findViewById(R.id.imgvNews)
        test_tittleNews = root.findViewById(R.id.test_tittleNews)
        test_topicNews = root.findViewById(R.id.test_topicNews)
        test_sourceNews = root.findViewById(R.id.test_sourceNews)
        val bundle3 = this.arguments
        if (bundle3 != null) {
            val tittlenews = bundle3.getString("tittleNews", "defaultValue")
            val topicnews = bundle3.getString("topicNews", "defaultValue")
            val sourceNews = bundle3.getString("sourceNews", "defaultValue")
            val imageUrlNews = bundle3.getString("imageUrlNews", "defaultValue")
            test_sourceNews.movementMethod = LinkMovementMethod.getInstance()

            test_tittleNews.text = tittlenews
            test_topicNews.text = topicnews

            test_sourceNews.text =  Html.fromHtml("<a href='$sourceNews'>المصدر</a>")
            test_sourceNews.movementMethod = LinkMovementMethod.getInstance()


                if (imageUrlNews.isEmpty()) {
                    Picasso.get()
                        .load(R.drawable.ic_baseline_image_24)
                        .into(imgvNews)

                } else {

                    Picasso.get()
                        .load(imageUrlNews)
                        .into(imgvNews)


                }

        }
        return root
    }


}