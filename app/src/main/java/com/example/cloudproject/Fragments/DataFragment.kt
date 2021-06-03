package com.example.cloudproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cloudproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
lateinit var deleteBtn: Button
private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


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
        deleteBtn = root.findViewById(R.id.deleteBtn)

        val bundle = this.arguments
        if (bundle != null) {
            val tittle = bundle.getString("tittle", "defaultValue")
            val topic = bundle.getString("topic", "defaultValue")
            val docid = bundle.getString("docid", "defaultValue")
            val imageUrl = bundle.getString("imageUrl", "defaultValue")


            var tittleTv = root.findViewById<TextView>(R.id.test_tittle)

            var test_topic = root.findViewById<TextView>(R.id.test_topic)



            tittleTv.text = tittle
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

            deleteBtn.setOnClickListener {
                db.collection("data").document(docid).delete()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, HomeFragment())

                    .commit()


            }

            root.findViewById<Button>(R.id.editBtn).setOnClickListener {
                val fragment: Fragment = EditDataFragment()
                val bundle2 = Bundle()
                bundle2.putString("tittle", tittle);
                bundle2.putString("topic", topic);
                bundle2.putString("docid", docid);

                Log.e("nnn", docid)

                fragment.arguments = bundle2;

                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, fragment)
                    .addToBackStack(null)

                    .commit()


            }


        }


        return root
    }


}