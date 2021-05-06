package com.example.cloudproject.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.cloudproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
lateinit var tittle: EditText
lateinit var topic: EditText
lateinit var btnAdd: Button
private val PICK_IMAGE_REQUEST = 71
private var filePath: Uri? = null
private var firebaseStore: FirebaseStorage? = null
private var storageReference: StorageReference? = null
lateinit var btn_choose_image: Button
lateinit var image_preview: ImageView

/**
 * A simple [Fragment] subclass.
 * Use the [AddTopicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTopicFragment : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_add_topic, container, false)
        val add = HashMap<String, Any>()
        tittle = root.findViewById(R.id.tittle)
        topic = root.findViewById(R.id.topic)
        btnAdd = root.findViewById(R.id.btnAdd)
        btn_choose_image = root.findViewById(R.id.btn_choose_image)
        image_preview = root.findViewById(R.id.image_preview)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference


        btn_choose_image.setOnClickListener {

            launchGallery()

        }

        btnAdd.setOnClickListener {


            uploadImage()
        }



        return root
    }


    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)
                image_preview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private  fun getData(){

        db.collection("data")
                .get().addOnSuccessListener{result->
                    for(document in result){
                        Log.d("TAG","${document.id}=>${document.data}")
                    }
                }
                .addOnFailureListener{exception->
                    Log.w("TAG","Error getting documents.",exception)
                }

    }


    private fun addUploadRecordToDb(uri: String) {

        val add = HashMap<String, Any>()
        add["imageUrl"] = uri
        add["tittle"] = tittle.text.toString()
        add["topic"] = topic.text.toString()

        db.collection("data")
                .add(add)
                .addOnSuccessListener {
                    Toast.makeText(activity!!, "Saved to DB", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity!!, "Error saving to DB", Toast.LENGTH_LONG).show()
                }


    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener {

            }
        } else {
            Toast.makeText(activity!!, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


}