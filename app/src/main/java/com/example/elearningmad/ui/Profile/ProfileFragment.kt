package com.example.elearningmad.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elearningmad.databinding.FragmentCoursesBinding
import com.example.elearningmad.databinding.FragmentProfileBinding
import com.example.elearningmad.ui.EditProfile

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val btn: Button = binding.ProfileEditButton
        btn.setOnClickListener {
            val intent = Intent(requireContext(), EditProfile::class.java)

            // Call the startActivity method and pass the Intent object as an argument
            startActivity(intent)
        }
//        val button = findViewById<Button>(R.id.button1)

//        button.setOnClickListener {
//            // Create an Intent object that specifies the activity to navigate to
//            val intent = Intent(this, InitialPage::class.java)
//
//            // Call the startActivity method and pass the Intent object as an argument
//            startActivity(intent)
//        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}