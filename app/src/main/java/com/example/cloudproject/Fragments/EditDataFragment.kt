package com.example.cloudproject.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cloudproject.Models.DataModel
import com.example.cloudproject.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var tittleEdit: EditText
lateinit var topicEdit: EditText
private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
private val PICK_IMAGE_REQUEST = 71
private var filePath: Uri? = null
private var firebaseStore: FirebaseStorage? = null
private var storageReference: StorageReference? = null
lateinit var btn_update_image: Button
lateinit var image_preview2: ImageView


/**
 * A simple [Fragment] subclass.
 * Use the [EditDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var editBtn: Button

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
        val root = inflater.inflate(R.layout.fragment_edit_data, container, false)
        val bundle2 = this.arguments
        tittleEdit = root.findViewById(R.id.tittleEdit)
        topicEdit = root.findViewById(R.id.topicEdit)
        editBtn = root.findViewById<Button>(R.id.editBtn)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        image_preview2 = root.findViewById(R.id.image_preview2)


        if (bundle2 != null) {

            var title = bundle2.getString("tittle", "")
            var topic = bundle2.getString("topic", "")
            var docid = bundle2.getString("docid", "")

            Log.e("nnn", docid)
            tittleEdit.setText(title)
            topicEdit.setText(topic)

            btn_update_image = root.findViewById(R.id.btn_update_image)
            btn_update_image.setOnClickListener {

                launchGallery()

            }


            editBtn.setOnClickListener {


                uploadImage(docid)
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment())
                        .commit()


            }


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
                image_preview2.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun UpdateData(uri: String, docid: String) {

        if (uri == "") {

            db.collection("data").document(docid).update("tittle", tittleEdit.text.toString(), "topic", topicEdit.text.toString())

        } else {
            db.collection("data").document(docid).update("tittle", tittleEdit.text.toString(), "topic", topicEdit.text.toString(), "imageUrl", uri)


        }

    }


    private fun uploadImage(doccid: String) {
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
                    UpdateData(downloadUri.toString(), doccid)
                } else {

                }
            }?.addOnFailureListener {

            }
        } else {
            UpdateData("", doccid)
        }
    }


}