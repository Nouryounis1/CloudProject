package com.example.cloudproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.DataAdapter
import com.example.cloudproject.Models.DataModel
import com.example.cloudproject.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
private val collectionReference: CollectionReference = db.collection("data")
var dataAdapter: DataAdapter? = null

lateinit var recyclerView: RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)

        setUpRecyleView()


        // Inflate the layout for this fragment
        return root
    }


    fun setUpRecyleView() {
        val query: Query = collectionReference




        val firseStoreRecylerOptions: FirestoreRecyclerOptions<DataModel> =
                FirestoreRecyclerOptions.Builder<DataModel>().setQuery(query, DataModel::class.java)
                        .build()
        dataAdapter = DataAdapter(firseStoreRecylerOptions)

        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = dataAdapter
    }


    override fun onStart() {
        super.onStart()
        dataAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        dataAdapter!!.stopListening()
    }

}