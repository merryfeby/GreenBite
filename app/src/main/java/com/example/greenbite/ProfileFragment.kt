package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        userViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvNameUser.text = "${user?.name ?: "Guest"}"
            binding.tvUserEmail.text = "${user?.email ?: "Guest"}"
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.btnLogoutProfile.setOnClickListener(){
            Toast.makeText(requireContext(), "Signing out...", Toast.LENGTH_SHORT).show()

            userViewModel.logout()

            findNavController().navigate(R.id.loginFragment)

            findNavController().popBackStack(R.id.loginFragment, false)
        }


        return binding.root
    }

}