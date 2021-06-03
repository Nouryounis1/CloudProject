package com.example.cloudproject.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.Adapters.NewsAdapter
import com.example.cloudproject.DataAdapter
import com.example.cloudproject.Models.DataModel
import com.example.cloudproject.Models.NewsModel
import com.example.cloudproject.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
private val collectionReference: CollectionReference = db.collection("news")
var newsAdapter: NewsAdapter? = null
lateinit var recyclerView2 : RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView2 = root.findViewById(R.id.recyclerView2)
        setUpRecyleView()
        return root
    }


    fun setUpRecyleView() {
        val query: Query = collectionReference




        val firseStoreRecylerOptions: FirestoreRecyclerOptions<NewsModel> =
            FirestoreRecyclerOptions.Builder<NewsModel>().setQuery(query, NewsModel::class.java)
                .build()
        newsAdapter = NewsAdapter(firseStoreRecylerOptions)

        recyclerView2.layoutManager = LinearLayoutManager(activity!!)
        recyclerView2.adapter = newsAdapter
    }


    override fun onStart() {
        super.onStart()
        newsAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsAdapter!!.stopListening()
    }


}