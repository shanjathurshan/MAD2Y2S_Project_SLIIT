package com.example.elearningmad.ui.Profile

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elearningmad.R
import com.example.elearningmad.databinding.FragmentProfileBinding
import com.example.elearningmad.ui.EditProfile
import com.example.elearningmad.ui.data.model.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var db: FirebaseFirestore

    lateinit var loadingPB: ProgressBar
    lateinit var userDetails : Students

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        db = FirebaseFirestore.getInstance();
        var user = FirebaseAuth.getInstance().currentUser


//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        loadingPB = binding.loader
        var layout01 = binding.layout01
        var layout02 = binding.layout02
        var layout03 = binding.layout03
        var profileName: TextView = binding.profileName
        var university: TextView = binding.university
        var email: TextView = binding.email
        var phone: TextView = binding.phone

        loadingPB.setVisibility(View.VISIBLE)
        layout01.setVisibility(View.GONE)
        layout02.setVisibility(View.GONE)
        layout03.setVisibility(View.GONE)


        val btn: Button = binding.ProfileEditButton
        btn.setOnClickListener {
            val intent = Intent(requireContext(), EditProfile::class.java)
            intent.putExtra("username", userDetails.username)
            intent.putExtra("email", userDetails.email)
            intent.putExtra("phone", userDetails.phone)
            intent.putExtra("university", userDetails.university)
            startActivity(intent)
        }

        if(user != null){
            Log.d(ContentValues.TAG, "User connected!")
            Log.d(ContentValues.TAG, user.uid!!)
        } else {
            Log.d(ContentValues.TAG, "Not connected!")
        }

        db!!.collection("users")
            .whereEqualTo("email", user?.email!!)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    userDetails = Students(
                        document.data["username"].toString(),
                        document.data["email"].toString(),
                        "null",
                        document.data["university"].toString(),
                        document.data["phone"].toString()
                    )
                    profileName.text = document.data["username"].toString()
                    university.text = document.data["university"].toString()
                    email.text = document.data["email"].toString()
                    phone.text = document.data["phone"].toString()

                    loadingPB.setVisibility(View.GONE)
                    layout01.setVisibility(View.VISIBLE)
                    layout02.setVisibility(View.VISIBLE)
                    layout03.setVisibility(View.VISIBLE)

                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}