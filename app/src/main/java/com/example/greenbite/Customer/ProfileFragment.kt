package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val usersViewModel: UsersViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersViewModel = usersViewModel

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvNameUser.text = "${user?.name ?: "Guest"}"
            binding.tvUserEmail.text = "${user?.email ?: "Guest"}"
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.btnMyBalance.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_customerTopUpFragment)
        }
        binding.btnLogoutProfile.setOnClickListener(){
            Toast.makeText(requireContext(), "Signing out...", Toast.LENGTH_SHORT).show()

            usersViewModel.logout()

            findNavController().navigate(R.id.action_global_loginFragment)

        }


        return binding.root
    }

}