package com.example.cloudproject.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cloudproject.R
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_data, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val tittle = bundle.getString("tittle", "defaultValue")
            val topic = bundle.getString("topic", "defaultValue")
            val imageUrl = bundle.getString("imageUrl", "defaultValue")




            var tittleTv = root.findViewById<TextView>(R.id.test_tittle)
            tittleTv.text = tittle

            var test_topic = root.findViewById<TextView>(R.id.test_topic)
            test_topic.text = topic

            var imgv = root.findViewById<ImageView>(R.id.imgv)

            if (imageUrl.isEmpty()) {
                Picasso.get()
                    .load(R.drawable.ic_baseline_image_24)
                    .into(imgv)

            } else {

                Picasso.get()
                    .load(imageUrl)
                    .into(imgv)


            }



            root.findViewById<Button>(R.id.editBtn).setOnClickListener {


                activity!!.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, EditDataFragment())
                    commit()
                }


            }



        }


        return root
    }


}